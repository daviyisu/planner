package com._8.planner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PlannerApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockHttpServletRequestBuilder auth(MockHttpServletRequestBuilder request) {
        return request.with(httpBasic("admin", "admin123"));
    }

    private long createUser(String name, String email) throws Exception {
        String body = mockMvc.perform(auth(post("/users"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\",\"email\":\"" + email + "\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(body, "$.id")).longValue();
    }

    private long createEvent(String name, String eventDate) throws Exception {
        String body = mockMvc.perform(auth(post("/events"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\",\"description\":\"desc\",\"eventDate\":\"" + eventDate + "\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(body, "$.id")).longValue();
    }

    @Test
    void requestsWithoutCredentialsAreRejected() throws Exception {
        mockMvc.perform(get("/events")).andExpect(status().isUnauthorized());
    }

    @Test
    void subscribeAndUnsubscribeLifecycle() throws Exception {
        long userId = createUser("Ana", "ana@example.com");
        long eventId = createEvent("Launch party", "2027-01-15T20:00:00Z");

        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isNoContent());
        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(auth(delete("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isNoContent());
        mockMvc.perform(auth(delete("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isNotFound());

        // Can subscribe again after unsubscribing.
        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isNoContent());
    }

    @Test
    void subscribeToUnknownUserOrEventReturnsNotFound() throws Exception {
        long userId = createUser("Bea", "bea@example.com");
        long eventId = createEvent("Meetup", "2027-02-01T18:00:00Z");

        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", userId, 999_999L)))
                .andExpect(status().isNotFound());
        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", 999_999L, eventId)))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchingUserKeepsCreatedAtAndExistingSubscriptions() throws Exception {
        long userId = createUser("Carlos", "carlos@example.com");
        long eventId = createEvent("Conference", "2027-03-10T09:00:00Z");
        mockMvc.perform(auth(post("/users/{u}/subscribe/{e}", userId, eventId)))
                .andExpect(status().isNoContent());

        Object createdAtBefore = jdbcTemplate.queryForObject(
                "select created_at from users where id = ?", Object.class, userId);
        Map<String, Object> subscriptionBefore = jdbcTemplate.queryForMap(
                "select id, subscribed_at from event_subscriptions where user_id = ?", userId);

        mockMvc.perform(auth(patch("/users/{id}", userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Carlos M.\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Carlos M."))
                .andExpect(jsonPath("$.email").value("carlos@example.com"));

        Object createdAtAfter = jdbcTemplate.queryForObject(
                "select created_at from users where id = ?", Object.class, userId);
        Map<String, Object> subscriptionAfter = jdbcTemplate.queryForMap(
                "select id, subscribed_at from event_subscriptions where user_id = ?", userId);

        assertThat(createdAtBefore).isNotNull();
        assertThat(createdAtAfter).isEqualTo(createdAtBefore);
        assertThat(subscriptionAfter).isEqualTo(subscriptionBefore);
    }

    @Test
    void invalidPayloadsAreRejected() throws Exception {
        mockMvc.perform(auth(post("/users"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(auth(post("/events"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"No date\"}"))
                .andExpect(status().isBadRequest());

        long userId = createUser("Dani", "dani@example.com");
        mockMvc.perform(auth(patch("/users/{id}", userId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"broken\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eventsCanBeFetchedAndListed() throws Exception {
        long eventId = createEvent("Workshop", "2027-04-20T10:00:00Z");

        mockMvc.perform(auth(get("/events/{id}", eventId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId))
                .andExpect(jsonPath("$.name").value("Workshop"));

        mockMvc.perform(auth(get("/events/{id}", 999_999L)))
                .andExpect(status().isNotFound());

        mockMvc.perform(auth(get("/events")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == " + eventId + ")].name").value("Workshop"));
    }
}

package com._8.planner.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com._8.planner.users.domain.exceptions.AlreadySubscribedException;
import com._8.planner.users.domain.exceptions.NotSubscribedException;

class UserTest {

    @Test
    void subscribingTwiceToSameEventFails() {
        User user = new User(1L, "Ana", "ana@example.com");
        user.subscribeTo(10L);

        assertThatThrownBy(() -> user.subscribeTo(10L)).isInstanceOf(AlreadySubscribedException.class);
        assertThat(user.getSubscriptions()).hasSize(1);
    }

    @Test
    void unsubscribeRemovesSubscription() {
        User user = new User(1L, "Ana", "ana@example.com");
        user.subscribeTo(10L);
        user.subscribeTo(20L);

        user.unsubscribeFrom(10L);

        assertThat(user.getSubscriptions()).extracting(EventSubscription::getEventId).containsExactly(20L);
    }

    @Test
    void unsubscribeFromUnknownEventFails() {
        User user = new User(1L, "Ana", "ana@example.com");

        assertThatThrownBy(() -> user.unsubscribeFrom(10L)).isInstanceOf(NotSubscribedException.class);
    }
}

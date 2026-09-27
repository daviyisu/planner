package com._8.planner.events.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._8.planner.events.domain.Event;
import com._8.planner.events.ports.in.CreateEventUseCase;
import com._8.planner.events.ports.in.FindAllEventsUseCase;
import com._8.planner.events.ports.in.FindEventByIdUseCase;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final FindEventByIdUseCase findEventByIdUseCase;
    private final FindAllEventsUseCase findAllEventsUseCase;

    @PostMapping("")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest newEventRequest) {
        final Event createdEvent = createEventUseCase.createEvent(newEventRequest.getName(), newEventRequest.getDescription(), newEventRequest.getEventDate());
        EventResponse eventResponse = EventHttpMapper.toResponse(createdEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(EventHttpMapper.toResponse(findEventByIdUseCase.findById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(findAllEventsUseCase.findAll().stream().map(EventHttpMapper::toResponse).toList());
    }
}

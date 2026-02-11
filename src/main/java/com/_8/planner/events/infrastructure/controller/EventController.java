package com._8.planner.events.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._8.planner.events.domain.Event;
import com._8.planner.events.ports.in.CreateEventUseCase;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private final CreateEventUseCase createEventUseCase;

    @PostMapping("")
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest newEventRequest) {
        final Event createdEvent = createEventUseCase.createEvent(newEventRequest.getName(), newEventRequest.getDescription(), newEventRequest.getEventDate());
        EventResponse eventResponse = EventHttpMapper.toResponse(createdEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }
    
}

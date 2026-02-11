package com._8.planner.events.domain.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Event with id " + id + " not found");
    }
}

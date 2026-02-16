package com._8.planner.events.infrastructure.controller;

import com._8.planner.events.domain.Event;

public class EventHttpMapper {
    public static EventResponse toResponse(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getDescription(), event.getEventDate());
    }
}

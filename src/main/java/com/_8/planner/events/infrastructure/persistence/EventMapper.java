package com._8.planner.events.infrastructure.persistence;

import com._8.planner.events.domain.Event;

public class EventMapper {
    public static Event toDomain(EventEntity entity) {
        return new Event(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getEventDate()
        );
    }

    public static EventEntity toEntity(Event event) {
        return new EventEntity(
            event.getId(),
            event.getName(),
            event.getDescription(),
            event.getEventDate()
        );
    }
}

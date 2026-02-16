package com._8.planner.events.ports.out;

import java.util.Optional;

import com._8.planner.events.domain.Event;

public interface EventRepository {
    Optional<Event> findById(Long eventId);
    Event save(Event newEvent);
}

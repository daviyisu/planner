package com._8.planner.events.ports.out;

import java.util.List;
import java.util.Optional;

import com._8.planner.events.domain.Event;

public interface EventRepository {
    Optional<Event> findById(Long eventId);
    List<Event> findAll();
    Event save(Event newEvent);
}

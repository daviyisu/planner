package com._8.planner.events.ports.in;

import java.util.List;

import com._8.planner.events.domain.Event;

public interface FindAllEventsUseCase {
    List<Event> findAll();
}

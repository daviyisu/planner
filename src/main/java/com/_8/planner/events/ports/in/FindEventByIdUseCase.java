package com._8.planner.events.ports.in;

import com._8.planner.events.domain.Event;

public interface FindEventByIdUseCase {
    Event findById(Long id);
}

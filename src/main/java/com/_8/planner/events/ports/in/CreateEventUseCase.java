package com._8.planner.events.ports.in;

import java.util.Date;

import com._8.planner.events.domain.Event;

public interface CreateEventUseCase {
    Event createEvent(String name, String description, Date evenDate);
}

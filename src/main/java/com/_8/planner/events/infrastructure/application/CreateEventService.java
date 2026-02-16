package com._8.planner.events.infrastructure.application;

import java.util.Date;

import org.springframework.stereotype.Service;

import com._8.planner.events.domain.Event;
import com._8.planner.events.ports.in.CreateEventUseCase;
import com._8.planner.events.ports.out.EventRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CreateEventService implements CreateEventUseCase {
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(String name, String description, Date eventDate) {
        Event newEvent = new Event(null, name, description, eventDate);
        return eventRepository.save(newEvent);
    }
}

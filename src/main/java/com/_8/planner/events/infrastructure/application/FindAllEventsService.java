package com._8.planner.events.infrastructure.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com._8.planner.events.domain.Event;
import com._8.planner.events.ports.in.FindAllEventsUseCase;
import com._8.planner.events.ports.out.EventRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FindAllEventsService implements FindAllEventsUseCase {
    private final EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}

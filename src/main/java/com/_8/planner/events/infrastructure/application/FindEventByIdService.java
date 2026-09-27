package com._8.planner.events.infrastructure.application;

import org.springframework.stereotype.Service;

import com._8.planner.events.domain.Event;
import com._8.planner.events.domain.exceptions.EventNotFoundException;
import com._8.planner.events.ports.in.FindEventByIdUseCase;
import com._8.planner.events.ports.out.EventRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FindEventByIdService implements FindEventByIdUseCase {
    private final EventRepository eventRepository;

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }
}

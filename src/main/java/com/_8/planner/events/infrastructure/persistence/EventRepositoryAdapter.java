package com._8.planner.events.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com._8.planner.events.domain.Event;
import com._8.planner.events.ports.out.EventRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EventRepositoryAdapter implements EventRepository {
    private final EventJpaRepository jpaRepository;

    @Override
    public Event save(Event newEvent) {
        EventEntity eventEntity = EventMapper.toEntity(newEvent);
        EventEntity savedEntity = jpaRepository.save(eventEntity);
        return EventMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return jpaRepository.findById(eventId).map(EventMapper::toDomain);
    }
}

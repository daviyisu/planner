package com._8.planner.users.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com._8.planner.users.ports.out.EventSubscriptionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EventSubscriptionRepositoryAdapter implements EventSubscriptionRepository {
    private final EventSubscriptionJpaRepository eventSubscriptionJpaRepository;

    @Override
    public Boolean existsByUserIdAndEventId(Long userId, Long eventId) {
        return eventSubscriptionJpaRepository.existsByUserIdAndEventId(userId, eventId);
    }
}

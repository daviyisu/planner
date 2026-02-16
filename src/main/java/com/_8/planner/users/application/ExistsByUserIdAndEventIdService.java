package com._8.planner.users.application;

import org.springframework.stereotype.Service;

import com._8.planner.users.ports.in.ExistsByUserIdAndEventIdUseCase;
import com._8.planner.users.ports.out.EventSubscriptionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExistsByUserIdAndEventIdService implements ExistsByUserIdAndEventIdUseCase {
    public EventSubscriptionRepository eventSubscriptionRepository;

    public Boolean existsByUserIdAndEventId(Long userId, Long eventId) {
        return eventSubscriptionRepository.existsByUserIdAndEventId(userId, eventId);
    }
}

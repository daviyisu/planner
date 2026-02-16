package com._8.planner.users.ports.out;

public interface EventSubscriptionRepository {
    Boolean existsByUserIdAndEventId(Long userId, Long eventId);
}

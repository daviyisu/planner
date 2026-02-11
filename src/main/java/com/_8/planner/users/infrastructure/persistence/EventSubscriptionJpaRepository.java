package com._8.planner.users.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSubscriptionJpaRepository extends JpaRepository<EventSubscriptionEntity, Long> {
    Boolean existsByUserIdAndEventId(Long userId, Long eventId);
}

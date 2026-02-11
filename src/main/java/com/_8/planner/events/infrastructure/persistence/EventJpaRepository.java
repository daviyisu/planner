package com._8.planner.events.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findById(Long eventId);
}

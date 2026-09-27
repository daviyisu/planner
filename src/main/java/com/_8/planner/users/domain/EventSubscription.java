package com._8.planner.users.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EventSubscription {
    @EqualsAndHashCode.Include
    private final Long userId;
    @EqualsAndHashCode.Include
    private final Long eventId;
    private final Instant subscribedAt;

    public static EventSubscription create(Long userId, Long eventId) {
        if (userId == null || eventId == null) {
            throw new IllegalArgumentException("UserId y EventId no pueden ser null");
        }

        return new EventSubscription(userId, eventId, Instant.now());
    }
}

package com._8.planner.users.ports.in;

public interface UnsubscribeFromEventUseCase {
    void unsubscribeFromEvent(Long userId, Long eventId);
}

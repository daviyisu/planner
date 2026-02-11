package com._8.planner.users.ports.in;

public interface SubscribeToEventUseCase {
    void subscribeToEvent(Long userId, Long event);
}

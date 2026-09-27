package com._8.planner.users.domain.exceptions;

public class NotSubscribedException extends RuntimeException {
    public NotSubscribedException(Long userId, Long eventId) {
        super("User with id " + userId + " is not subscribed to event with id " + eventId);
    }
}

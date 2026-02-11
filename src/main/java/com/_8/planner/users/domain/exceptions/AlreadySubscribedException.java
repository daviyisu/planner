package com._8.planner.users.domain.exceptions;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(Long userId, Long eventId) {
        super("User with id " + userId + " already is subscribed to event with id " + eventId
            
        );
    }
}

package com._8.planner.users.domain;

import java.util.HashSet;
import java.util.Set;

import com._8.planner.users.domain.exceptions.AlreadySubscribedException;
import com._8.planner.users.domain.exceptions.NotSubscribedException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private String name;
    private String email;
    private Set<EventSubscription> subscriptions = new HashSet<>();

    public void changeName(String newName) {
        if (newName != null && !newName.isBlank()) {
            this.name = newName;
        }
    }

    public void changeEmail(String newEmail) {
        if (newEmail != null && !newEmail.isBlank()) {
            this.email = newEmail;
        }
    }

    public void subscribeTo(Long eventId) {
        EventSubscription subscription = EventSubscription.create(this.id, eventId);
        if (!subscriptions.add(subscription)) {
            throw new AlreadySubscribedException(this.id, eventId);
        }
    }

    public void unsubscribeFrom(Long eventId) {
        if (!subscriptions.removeIf(sub -> sub.getEventId().equals(eventId))) {
            throw new NotSubscribedException(this.id, eventId);
        }
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

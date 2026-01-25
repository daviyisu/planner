package com._8.planner.users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private String name;
    private String email;

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
}

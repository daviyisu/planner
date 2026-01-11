package com._8.planner.users.infrastructure.controller;

import com._8.planner.users.domain.User;

public class UserHttpMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}

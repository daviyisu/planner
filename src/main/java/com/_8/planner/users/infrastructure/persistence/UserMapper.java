package com._8.planner.users.infrastructure.persistence;

import com._8.planner.users.domain.User;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getName(),
            entity.getEmail()
        );
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}

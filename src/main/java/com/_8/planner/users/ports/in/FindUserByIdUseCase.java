package com._8.planner.users.ports.in;

import java.util.Optional;

import com._8.planner.users.domain.User;

public interface FindUserByIdUseCase {
    Optional<User> findById(Long id);
}
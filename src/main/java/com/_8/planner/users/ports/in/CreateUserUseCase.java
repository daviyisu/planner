package com._8.planner.users.ports.in;

import com._8.planner.users.domain.User;

public interface CreateUserUseCase {
    User createUser(String name, String email);
}

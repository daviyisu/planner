package com._8.planner.users.ports.in;

import com._8.planner.users.domain.User;

public interface PatchUserUseCase {
    User patchUser(Long id, PatchUserCommand patchUserCommand);
}

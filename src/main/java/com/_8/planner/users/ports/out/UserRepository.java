package com._8.planner.users.ports.out;

import java.util.Optional;
import com._8.planner.users.domain.User;

public interface UserRepository {
    Optional<User> findById(Long id);
}

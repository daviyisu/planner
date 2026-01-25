package com._8.planner.users.application;

import org.springframework.stereotype.Service;

import com._8.planner.users.ports.in.CreateUserUseCase;
import com._8.planner.users.ports.out.UserRepository;
import com._8.planner.users.domain.User;

@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email) {
        User newUser = new User(null, name, email);
        return userRepository.save(newUser);
    }
}

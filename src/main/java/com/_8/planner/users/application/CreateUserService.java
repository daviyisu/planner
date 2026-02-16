package com._8.planner.users.application;

import org.springframework.stereotype.Service;

import com._8.planner.users.ports.in.CreateUserUseCase;
import com._8.planner.users.ports.out.UserRepository;

import lombok.AllArgsConstructor;

import com._8.planner.users.domain.User;

@AllArgsConstructor
@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public User createUser(String name, String email) {
        User newUser = new User(null, name, email);
        return userRepository.save(newUser);
    }
}

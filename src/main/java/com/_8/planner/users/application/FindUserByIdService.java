package com._8.planner.users.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com._8.planner.users.domain.User;
import com._8.planner.users.ports.in.FindUserByIdUseCase;
import com._8.planner.users.ports.out.UserRepository;

@Service
public class FindUserByIdService implements FindUserByIdUseCase {
    private final UserRepository userRepository;
    
    public FindUserByIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}

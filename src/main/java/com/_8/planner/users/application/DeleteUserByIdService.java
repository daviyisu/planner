package com._8.planner.users.application;

import org.springframework.stereotype.Service;

import com._8.planner.users.ports.in.DeleteUserByIdUseCase;
import com._8.planner.users.ports.out.UserRepository;

@Service
public class DeleteUserByIdService implements DeleteUserByIdUseCase {
    private final UserRepository userRepository;

    public DeleteUserByIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

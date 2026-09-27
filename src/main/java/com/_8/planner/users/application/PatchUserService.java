package com._8.planner.users.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._8.planner.users.domain.User;
import com._8.planner.users.domain.exceptions.UserNotFoundException;
import com._8.planner.users.ports.in.PatchUserCommand;
import com._8.planner.users.ports.in.PatchUserUseCase;
import com._8.planner.users.ports.out.UserRepository;

@Service
public class PatchUserService implements PatchUserUseCase {
    private final UserRepository userRepository;

    public PatchUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User patchUser(Long id, PatchUserCommand patchUserCommand) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        patchUserCommand.getName().ifPresent(user::changeName);
        patchUserCommand.getEmail().ifPresent(user::changeEmail);
        return userRepository.save(user);
    }
}

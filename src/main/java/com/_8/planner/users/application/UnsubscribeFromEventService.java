package com._8.planner.users.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._8.planner.users.domain.User;
import com._8.planner.users.domain.exceptions.UserNotFoundException;
import com._8.planner.users.ports.in.UnsubscribeFromEventUseCase;
import com._8.planner.users.ports.out.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UnsubscribeFromEventService implements UnsubscribeFromEventUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void unsubscribeFromEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.unsubscribeFrom(eventId);
        userRepository.save(user);
    }
}

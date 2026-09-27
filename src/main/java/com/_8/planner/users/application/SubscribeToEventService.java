package com._8.planner.users.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._8.planner.events.domain.Event;
import com._8.planner.events.domain.exceptions.EventNotFoundException;
import com._8.planner.events.ports.out.EventRepository;
import com._8.planner.users.domain.User;
import com._8.planner.users.domain.exceptions.AlreadySubscribedException;
import com._8.planner.users.domain.exceptions.UserNotFoundException;
import com._8.planner.users.ports.in.SubscribeToEventUseCase;
import com._8.planner.users.ports.out.EventSubscriptionRepository;
import com._8.planner.users.ports.out.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubscribeToEventService implements SubscribeToEventUseCase {
    public UserRepository userRepository;
    public EventRepository eventRepository;
    public EventSubscriptionRepository eventSubscriptionRepository;

    @Override
    @Transactional
    public void subscribeToEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (eventSubscriptionRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new AlreadySubscribedException(userId, eventId);
        }

        user.subscribeTo(event.getId());
        userRepository.save(user);
    }
}

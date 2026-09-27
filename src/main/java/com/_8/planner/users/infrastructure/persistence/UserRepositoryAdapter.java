package com._8.planner.users.infrastructure.persistence;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com._8.planner.events.infrastructure.persistence.EventEntity;
import com._8.planner.users.domain.EventSubscription;
import com._8.planner.users.domain.User;
import com._8.planner.users.ports.out.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository
                .findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            UserEntity savedEntity = jpaRepository.save(UserMapper.toEntity(user, entityManager));
            return UserMapper.toDomain(savedEntity);
        }

        // Update the managed entity in place so createdAt and existing
        // subscriptions (with their ids and subscribedAt) are preserved.
        UserEntity entity = jpaRepository.findById(user.getId())
                .orElseGet(() -> UserMapper.toEntity(user, entityManager));
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        syncSubscriptions(entity, user.getSubscriptions());

        return UserMapper.toDomain(jpaRepository.save(entity));
    }

    private void syncSubscriptions(UserEntity entity, Set<EventSubscription> subscriptions) {
        Set<Long> wantedEventIds = subscriptions.stream()
                .map(EventSubscription::getEventId)
                .collect(Collectors.toSet());

        entity.getSubscriptions().removeIf(sub -> !wantedEventIds.contains(sub.getEvent().getId()));

        Set<Long> currentEventIds = entity.getSubscriptions().stream()
                .map(sub -> sub.getEvent().getId())
                .collect(Collectors.toSet());

        wantedEventIds.stream()
                .filter(eventId -> !currentEventIds.contains(eventId))
                .forEach(eventId -> entity.getSubscriptions().add(
                        new EventSubscriptionEntity(entity, entityManager.getReference(EventEntity.class, eventId))));
    }
}

package com._8.planner.users.infrastructure.persistence;

import java.time.ZoneOffset;

import com._8.planner.events.infrastructure.persistence.EventEntity;
import com._8.planner.users.domain.EventSubscription;
import com._8.planner.users.domain.User;

import jakarta.persistence.EntityManager;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        User user = new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail());
        
        entity.getSubscriptions().forEach(sub -> {
            EventSubscription eventSubscription = new EventSubscription(sub.getUser().getId(), sub.getEvent().getId(), sub.getSubscribedAt().atZone(ZoneOffset.UTC).toInstant());
            user.getSubscriptions().add(eventSubscription);
        });
        return user;
    }

    public static UserEntity toEntity(User user, EntityManager em) {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail());

        user.getSubscriptions().forEach(sub -> {
            EventEntity eventRef = em.getReference(EventEntity.class, sub.getEventId());
            EventSubscriptionEntity eventSubscriptionEntity = new EventSubscriptionEntity(userEntity, eventRef);
            userEntity.getSubscriptions().add(eventSubscriptionEntity);
        });

        return userEntity;
    }
}

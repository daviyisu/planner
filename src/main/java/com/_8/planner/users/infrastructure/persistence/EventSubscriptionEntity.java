package com._8.planner.users.infrastructure.persistence;

import java.time.LocalDateTime;

import com._8.planner.events.infrastructure.persistence.EventEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event_subscriptions", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "event_id" }))
public class EventSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(updatable = false)
    private LocalDateTime subscribedAt;

    @PrePersist
    public void prePersistSubscribedAt() {
        this.setSubscribedAt(LocalDateTime.now());
    }

    public EventSubscriptionEntity(UserEntity userEntity, EventEntity eventEntity) {
        this.user = userEntity;
        this.event = eventEntity;
    }
}

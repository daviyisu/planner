package com._8.planner.events.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com._8.planner.users.infrastructure.persistence.EventSubscriptionEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Column(nullable = false)
    private Date eventDate;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventSubscriptionEntity> subscriptions = new HashSet<>();

    @PrePersist
    public void prePersistSubscribedAt() {
        this.setCreatedAt(LocalDateTime.now());
    }

    public EventEntity(Long id, String name, String description, Date eventDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
    }
}

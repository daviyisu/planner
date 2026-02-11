package com._8.planner.users.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

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
    public User save(User newUser) {
        UserEntity userEntity = UserMapper.toEntity(newUser, entityManager);
        UserEntity savedEntity = jpaRepository.save(userEntity);
        return UserMapper.toDomain(savedEntity);
    }
}

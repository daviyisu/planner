package com._8.planner.users.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com._8.planner.users.domain.User;
import com._8.planner.users.ports.out.UserRepository;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

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
}

package com._8.planner.users.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._8.planner.users.domain.User;
import com._8.planner.users.ports.in.CreateUserUseCase;
import com._8.planner.users.ports.in.DeleteUserByIdUseCase;
import com._8.planner.users.ports.in.FindUserByIdUseCase;
import com._8.planner.users.ports.in.PatchUserCommand;
import com._8.planner.users.ports.in.PatchUserUseCase;
import com._8.planner.users.ports.in.SubscribeToEventUseCase;

import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final FindUserByIdUseCase findUserById;
    private final DeleteUserByIdUseCase deleteUserById;
    private final CreateUserUseCase createUser;
    private final PatchUserUseCase patchUser;
    private final SubscribeToEventUseCase subscribeToEvent;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return findUserById
                .findById(id)
                .map(UserHttpMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        deleteUserById.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest newUserRequest) {
        final User newUser = createUser.createUser(newUserRequest.getName(), newUserRequest.getEmail());
        UserResponse userResponse = UserHttpMapper.toResponse(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest) {
        PatchUserCommand patchUserCommand = new PatchUserCommand(Optional.ofNullable(updateUserRequest.getName()),
                Optional.ofNullable(updateUserRequest.getEmail()));
        final User uptadedUser = patchUser.patchUser(id, patchUserCommand);
        UserResponse userResponse = UserHttpMapper.toResponse(uptadedUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }

    @PostMapping("/{userId}/subscribe/{eventId}")
    public ResponseEntity<Void> postMethodName(@PathVariable Long userId, @PathVariable Long eventId) {
        subscribeToEvent.subscribeToEvent(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}

package com._8.planner.users.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._8.planner.users.domain.User;
import com._8.planner.users.ports.in.CreateUserUseCase;
import com._8.planner.users.ports.in.DeleteUserByIdUseCase;
import com._8.planner.users.ports.in.FindUserByIdUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private final FindUserByIdUseCase findUserById;
    private final DeleteUserByIdUseCase deleteUserById;
    private final CreateUserUseCase createUser;

    public UserController(FindUserByIdUseCase findUserById, DeleteUserByIdUseCase deleteUserById,
            CreateUserUseCase createUser) {
        this.findUserById = findUserById;
        this.deleteUserById = deleteUserById;
        this.createUser = createUser;
    }

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

}

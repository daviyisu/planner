package com._8.planner.users.infrastructure.controller;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String name;
    @Email
    private String email;
}


package com._8.planner.users.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
}

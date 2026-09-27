package com._8.planner.events.infrastructure.controller;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEventRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Date eventDate;
}

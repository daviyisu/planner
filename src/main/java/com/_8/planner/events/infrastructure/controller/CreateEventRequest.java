package com._8.planner.events.infrastructure.controller;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEventRequest {
    private String name;
    private String description;
    private Date eventDate;
}

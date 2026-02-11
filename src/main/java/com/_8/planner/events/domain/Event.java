package com._8.planner.events.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Event {
    private final Long id;
    private String name;
    private String description;
    private Date eventDate;
}

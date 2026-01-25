package com._8.planner.users.ports.in;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchUserCommand {
    private final Optional<String> name;
    private final Optional<String> email;
}

package com._8.planner.users.ports.in;

public interface ExistsByUserIdAndEventIdUseCase {
    Boolean existsByUserIdAndEventId(Long userId, Long eventId);
}

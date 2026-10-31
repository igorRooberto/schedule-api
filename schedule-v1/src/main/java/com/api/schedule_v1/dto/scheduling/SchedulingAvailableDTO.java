package com.api.schedule_v1.dto.scheduling;

import java.time.LocalTime;

public record SchedulingAvailableDTO(

        LocalTime startTime,
        LocalTime endTime,
        boolean available
) {
}

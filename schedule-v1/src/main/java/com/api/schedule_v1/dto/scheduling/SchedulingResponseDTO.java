package com.api.schedule_v1.dto.scheduling;

import com.api.schedule_v1.domain.enums.SchedulingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SchedulingResponseDTO(
        long id,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime paymentDate,
        SchedulingStatus status
) {
}

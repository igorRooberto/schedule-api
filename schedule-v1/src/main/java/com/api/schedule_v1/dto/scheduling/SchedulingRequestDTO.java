package com.api.schedule_v1.dto.scheduling;

import java.time.LocalDate;
import java.time.LocalTime;

public record SchedulingRequestDTO(
        long clientId,
        long professionalId,
        long serviceId,
        LocalDate appointmentDate,
        LocalTime startTime

) {
}

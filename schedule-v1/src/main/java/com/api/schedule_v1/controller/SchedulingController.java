package com.api.schedule_v1.controller;

import com.api.schedule_v1.dto.scheduling.SchedulingAvailableDTO;
import com.api.schedule_v1.dto.scheduling.SchedulingRequestDTO;
import com.api.schedule_v1.services.SchedulingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<SchedulingAvailableDTO>> getAllForDate(
            @RequestParam Long professionalId,
            @RequestParam Long serviceId,
            @RequestParam LocalDate appointmentDate) {

        List<SchedulingAvailableDTO> availableSlots =
                schedulingService.getAllForDate(
                        professionalId,
                        serviceId,
                        appointmentDate
                );

        return ResponseEntity.ok(availableSlots);
    }

    @PostMapping
    public ResponseEntity<Void> scheduleAppointment(
            @RequestBody SchedulingRequestDTO dto) {

        schedulingService.scheduleAppointment(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

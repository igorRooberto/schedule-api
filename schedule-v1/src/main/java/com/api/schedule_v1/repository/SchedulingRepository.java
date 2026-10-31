package com.api.schedule_v1.repository;

import com.api.schedule_v1.domain.entities.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    @Query("SELECT scheduling FROM Scheduling scheduling WHERE scheduling.professional.id = :professionalId " +
            "AND scheduling.appointmentDate = :appointmentDate ORDER BY scheduling.startTime ASC")
    List<Scheduling> findByProfessionalIdAndAppointmentDate(@Param("professionalId") Long professionalId, @Param("appointmentDate") LocalDate appointmentDate);
}

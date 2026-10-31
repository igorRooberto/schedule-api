package com.api.schedule_v1.repository;

import com.api.schedule_v1.domain.entities.ProfessionalAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalAvailabilityRepository extends JpaRepository<ProfessionalAvailability, Long> {

    @Query("SELECT availability FROM ProfessionalAvailability availability JOIN FETCH availability.shift " +
            "WHERE availability.professional.id = :professionalId AND availability.dayOfWeek = :dayOfWeek")
    List<ProfessionalAvailability> findByProfessionalIdAndDayOfWeek(@Param("professionalId") Long professionalId, @Param("dayOfWeek") DayOfWeek dayOfWeek);
}

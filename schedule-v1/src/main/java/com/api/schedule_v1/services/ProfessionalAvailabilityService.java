package com.api.schedule_v1.services;

import com.api.schedule_v1.domain.entities.ProfessionalAvailability;
import com.api.schedule_v1.repository.ProfessionalAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class ProfessionalAvailabilityService {

    private final ProfessionalAvailabilityRepository professionalAvailabilityRepository;

    public ProfessionalAvailabilityService(ProfessionalAvailabilityRepository professionalAvailabilityRepository) {
        this.professionalAvailabilityRepository = professionalAvailabilityRepository;
    }

    public List<ProfessionalAvailability> findByProfessionalIdAndDayOfWeek(Long professionalId, DayOfWeek dayOfWeek){
        return professionalAvailabilityRepository.findByProfessionalIdAndDayOfWeek(professionalId, dayOfWeek);
    }
}

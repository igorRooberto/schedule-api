package com.api.schedule_v1.services;

import com.api.schedule_v1.domain.entities.Professional;
import com.api.schedule_v1.exception.NotFoundException;
import com.api.schedule_v1.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public Professional findById(Long id){
         return professionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional Não encontrado"));
    }
}

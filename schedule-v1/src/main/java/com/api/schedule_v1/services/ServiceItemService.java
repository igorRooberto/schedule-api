package com.api.schedule_v1.services;

import com.api.schedule_v1.domain.entities.ServiceItem;
import com.api.schedule_v1.exception.NotFoundException;
import com.api.schedule_v1.repository.ServiceItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceItemService {

    private final ServiceItemRepository serviceItemRepository;

    public ServiceItemService(ServiceItemRepository serviceItemRepository) {
        this.serviceItemRepository = serviceItemRepository;
    }

    public ServiceItem findById(long id){
        return serviceItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado"));
    }

}

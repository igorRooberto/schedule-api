package com.api.schedule_v1.services;

import com.api.schedule_v1.domain.entities.Client;
import com.api.schedule_v1.exception.NotFoundException;
import com.api.schedule_v1.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findById(long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente Não Encontrado"));
    }


}

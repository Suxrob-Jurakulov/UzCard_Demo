package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.ClientDTO;
import com.company.entity.ClientEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ClientRepository;
import com.company.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthService authService;

    public ClientDTO create(ClientDTO dto) {
        Optional<ClientEntity> optional = clientRepository.findByPassportSeriesAndPassportNumber(dto.getPassportSeries(), dto.getPassportNumber());
        if (optional.isPresent()) {
            throw new BadRequestException("This client already exist");
        }
        ClientEntity entity = new ClientEntity();
        return saveClient(entity, dto);
    }

    public ClientDTO update(ClientDTO dto) {
        ClientEntity entity = get(dto.getId());
        return saveClient(entity, dto);
    }

    private ClientDTO saveClient(ClientEntity entity, ClientDTO dto) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setPhone(dto.getPhone());
        entity.setPassportSeries(dto.getPassportSeries());
        entity.setPassportNumber(dto.getPassportNumber());
        clientRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public ClientEntity get(String id) {
        return clientRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Client not found");
        });
    }

    public ClientDTO getById(String clientId) {
        ClientEntity entity = get(clientId);
        return getDTO(entity);
    }

    private ClientDTO getDTO(ClientEntity entity){
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setMiddleName(entity.getMiddleName());
        dto.setPhone(entity.getPhone());
        dto.setPassportSeries(entity.getPassportSeries());
        dto.setPassportNumber(entity.getPassportNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ClientDTO getByIdBank(String clientId) {
        String bankId = authService.getCurrentUser().getId();
        ClientEntity entity = get(clientId);
//        if (entity.get)
        return null;
    }
}

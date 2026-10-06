package com.barberosw.api.services;

import com.barberosw.api.dto.ClienteRequest;
import com.barberosw.api.dto.ClienteResponse;
import com.barberosw.api.mappers.ClienteMapper;
import com.barberosw.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponse crear(ClienteRequest req) {
        if (clienteRepository.existsByEmailIgnoreCase(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        return ClienteMapper.toResponse(clienteRepository.save(ClienteMapper.toEntity(req)));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findByActivoTrue().stream()
                .map(ClienteMapper::toResponse)
                .toList();
    }
}
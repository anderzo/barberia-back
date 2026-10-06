package com.barberosw.api.controller;

import com.barberosw.api.dto.ClienteRequest;
import com.barberosw.api.dto.ClienteResponse;
import com.barberosw.api.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@Valid @RequestBody ClienteRequest req) {
        return clienteService.crear(req);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }
}
package com.barberosw.api.controller;

import com.barberosw.api.dto.CitaRequest;
import com.barberosw.api.dto.CitaResponse;
import com.barberosw.api.services.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse crear(@Valid @RequestBody CitaRequest req, Authentication auth) {
        return citaService.crear(req, auth.getName());
    }

    @GetMapping
    public List<CitaResponse> listar(Authentication auth) {
        return citaService.listar(auth.getName());
    }
}
package com.barberosw.api.controller;

import com.barberosw.api.dto.BarberoResponse;
import com.barberosw.api.dto.ServicioResponse;
import com.barberosw.api.services.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping("/servicios")
    public List<ServicioResponse> servicios() {
        return catalogoService.servicios();
    }

    @GetMapping("/barberos")
    public List<BarberoResponse> barberos() {
        return catalogoService.barberos();
    }
}

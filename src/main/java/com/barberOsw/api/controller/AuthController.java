package com.barberosw.api.controller;

import com.barberosw.api.dto.LoginRequest;
import com.barberosw.api.dto.LoginResponse;
import com.barberosw.api.dto.RegistroRequest;
import com.barberosw.api.dto.UsuarioResponse;
import com.barberosw.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registro(@Valid @RequestBody RegistroRequest req) {
        return authService.registrar(req);
    }
    //expone POST /api/auth/registro

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
    //expone POST /api/auth/login.
}
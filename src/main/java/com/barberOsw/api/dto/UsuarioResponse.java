package com.barberosw.api.dto;

import com.barberosw.api.models.Rol;

public record UsuarioResponse(Long id, String email, Rol rol) {}
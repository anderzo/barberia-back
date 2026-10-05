package com.barberosw.api.dto;

import com.barberosw.api.models.Rol;

public record LoginResponse(String token, String email, Rol rol) {}
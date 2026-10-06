package com.barberosw.api.dto;

public record ClienteResponse(Long id, String nombre, String apellido,
                              String email, String telefono, Boolean activo) {}

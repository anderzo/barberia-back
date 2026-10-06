package com.barberosw.api.dto;

import com.barberosw.api.models.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioAdminRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 100) String apellido,
        @Email @NotBlank @Size(max = 150) String email,
        @Size(max = 20) String telefono,
        @NotBlank @Size(min = 8) String password,
        @NotNull Rol rol
) {}
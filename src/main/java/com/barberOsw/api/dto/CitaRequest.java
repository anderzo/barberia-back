package com.barberosw.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record CitaRequest(
        Long clienteId,                    // solo lo usan ADMIN y BARBERO
        @NotNull Long barberoId,
        @NotNull Long servicioId,
        @NotNull OffsetDateTime inicio,
        @Size(max = 255) String notas
) {}
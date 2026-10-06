package com.barberosw.api.dto;

import com.barberosw.api.models.EstadoCita;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CitaResponse(Long id, String cliente, String barbero, String servicio,
                           OffsetDateTime inicio, OffsetDateTime fin,
                           EstadoCita estado, BigDecimal precio, String notas) {}

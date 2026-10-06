package com.barberosw.api.dto;

import java.math.BigDecimal;

public record ServicioResponse(Long id, String nombre, String descripcion,
                               Integer duracionMin, BigDecimal precio) {}
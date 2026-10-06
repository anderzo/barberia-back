package com.barberosw.api.dto;

import java.util.List;

public record BarberoResponse(Long id, String nombre, String apellido, List<Long> servicioIds) {}
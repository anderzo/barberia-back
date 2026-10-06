package com.barberosw.api.mappers;

import com.barberosw.api.dto.ClienteRequest;
import com.barberosw.api.dto.ClienteResponse;
import com.barberosw.api.models.Cliente;

public final class ClienteMapper {

    private ClienteMapper() {}

    public static Cliente toEntity(ClienteRequest r) {
        Cliente c = new Cliente();
        c.setNombre(r.nombre());
        c.setApellido(r.apellido());
        c.setEmail(r.email());
        c.setTelefono(r.telefono());
        return c;
    }

    public static ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNombre(), c.getApellido(),
                c.getEmail(), c.getTelefono(), c.getActivo());
    }
}
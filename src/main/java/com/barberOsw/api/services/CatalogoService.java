package com.barberosw.api.services;

import com.barberosw.api.dto.BarberoResponse;
import com.barberosw.api.dto.ServicioResponse;
import com.barberosw.api.models.Servicio;
import com.barberosw.api.repository.BarberoRepository;
import com.barberosw.api.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ServicioRepository servicioRepository;
    private final BarberoRepository barberoRepository;

    @Transactional(readOnly = true)
    public List<ServicioResponse> servicios() {
        return servicioRepository.findByActivoTrue().stream()
                .map(s -> new ServicioResponse(s.getId(), s.getNombre(), s.getDescripcion(),
                        s.getDuracionMin(), s.getPrecio()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BarberoResponse> barberos() {
        return barberoRepository.findByActivoTrue().stream()
                .map(b -> new BarberoResponse(b.getId(), b.getNombre(), b.getApellido(),
                        b.getServicios().stream().map(Servicio::getId).toList()))
                .toList();
    }
}
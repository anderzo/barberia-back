package com.barberosw.api.services;

import com.barberosw.api.dto.UsuarioAdminRequest;
import com.barberosw.api.dto.UsuarioResponse;
import com.barberosw.api.models.Barbero;
import com.barberosw.api.models.Cliente;
import com.barberosw.api.models.HorarioBarbero;
import com.barberosw.api.models.Usuarios;
import com.barberosw.api.repository.BarberoRepository;
import com.barberosw.api.repository.ClienteRepository;
import com.barberosw.api.repository.HorarioBarberoRepository;
import com.barberosw.api.repository.ServicioRepository;
import com.barberosw.api.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuariosRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final BarberoRepository barberoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServicioRepository servicioRepository;
    private final HorarioBarberoRepository horarioRepository;

    @Transactional
    public UsuarioResponse crear(UsuarioAdminRequest req) {
        if (usuarioRepository.existsByEmailIgnoreCase(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        Usuarios usuario = new Usuarios();
        usuario.setEmail(req.email());
        usuario.setPasswordHash(passwordEncoder.encode(req.password()));
        usuario.setRol(req.rol());

        switch (req.rol()) {
            case CLIENTE -> {
                Cliente c = new Cliente();
                c.setNombre(req.nombre());
                c.setApellido(req.apellido());
                c.setEmail(req.email());
                c.setTelefono(req.telefono());
                usuario.setCliente(clienteRepository.save(c));
            }
            case BARBERO -> {
            Barbero b = new Barbero();
            b.setNombre(req.nombre());
            b.setApellido(req.apellido());
            b.setTelefono(req.telefono());
            b.setServicios(new HashSet<>(servicioRepository.findByActivoTrue()));
            b = barberoRepository.save(b);

            for (short dia = 1; dia <= 6; dia++) {   // lunes a sábado, 9:00 a 18:00
                HorarioBarbero h = new HorarioBarbero();
                h.setBarbero(b);
                h.setDiaSemana(dia);
                h.setHoraInicio(LocalTime.of(9, 0));
                h.setHoraFin(LocalTime.of(18, 0));
                horarioRepository.save(h);
            }
            usuario.setBarbero(b);
        }
            case ADMIN -> { } // el admin no tiene perfil, solo cuenta
        }

        usuarioRepository.save(usuario);
        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), usuario.getRol());
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getEmail(), u.getRol()))
                .toList();
    }
}
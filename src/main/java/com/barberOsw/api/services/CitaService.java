package com.barberosw.api.services;

import com.barberosw.api.dto.CitaRequest;
import com.barberosw.api.dto.CitaResponse;
import com.barberosw.api.models.*;
import com.barberosw.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private static final ZoneId ZONA = ZoneId.of("America/Tegucigalpa");

    private final CitaRepository citaRepository;
    private final UsuariosRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final BarberoRepository barberoRepository;
    private final ServicioRepository servicioRepository;
    private final HorarioBarberoRepository horarioRepository;

    @Transactional
    public CitaResponse crear(CitaRequest req, String emailActual) {
        Usuarios actual = usuarioActual(emailActual);

        // 1. Cliente: el cliente usa su propio perfil; staff elige uno existente
        Cliente cliente;
        if (actual.getRol() == Rol.CLIENTE) {
            cliente = actual.getCliente();
            if (cliente == null) {
                throw error(HttpStatus.CONFLICT, "Tu cuenta no tiene perfil de cliente");
            }
        } else {
            if (req.clienteId() == null) {
                throw error(HttpStatus.BAD_REQUEST, "Debes indicar el cliente");
            }
            cliente = clienteRepository.findById(req.clienteId())
                    .filter(Cliente::getActivo)
                    .orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El cliente no existe o está inactivo"));
        }

        // 2. Barbero y servicio
        Barbero barbero = barberoRepository.findById(req.barberoId())
                .filter(Barbero::getActivo)
                .orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El barbero no existe o no está activo"));

        Servicio servicio = servicioRepository.findById(req.servicioId())
                .filter(Servicio::getActivo)
                .orElseThrow(() -> error(HttpStatus.NOT_FOUND, "El servicio no existe o no está activo"));

        boolean ofreceServicio = barbero.getServicios().stream()
                .anyMatch(s -> s.getId().equals(servicio.getId()));
        if (!ofreceServicio) {
            throw error(HttpStatus.BAD_REQUEST, "El barbero no ofrece ese servicio");
        }

        // 3. Fecha futura y dentro del horario del barbero
        OffsetDateTime inicio = req.inicio();
        OffsetDateTime fin = inicio.plusMinutes(servicio.getDuracionMin());

        if (!inicio.isAfter(OffsetDateTime.now())) {
            throw error(HttpStatus.BAD_REQUEST, "La cita debe ser en una fecha futura");
        }

        ZonedDateTime zi = inicio.atZoneSameInstant(ZONA);
        ZonedDateTime zf = fin.atZoneSameInstant(ZONA);
        boolean dentroDeHorario = zi.toLocalDate().equals(zf.toLocalDate())
                && horarioRepository.cubreHorario(
                        barbero.getId(), (short) zi.getDayOfWeek().getValue(),
                        zi.toLocalTime(), zf.toLocalTime());
        if (!dentroDeHorario) {
            throw error(HttpStatus.BAD_REQUEST, "El barbero no trabaja en ese horario");
        }

        // 4. Sin traslape con otra cita
        if (citaRepository.existeTraslape(barbero.getId(), inicio, fin, EstadoCita.CANCELADA)) {
            throw error(HttpStatus.CONFLICT, "El barbero ya tiene una cita en ese horario");
        }

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setServicio(servicio);
        cita.setInicio(inicio);
        cita.setFin(fin);
        cita.setPrecio(servicio.getPrecio());   // precio al momento de reservar
        cita.setNotas(req.notas());

        return toResponse(citaRepository.saveAndFlush(cita));
    }

    @Transactional(readOnly = true)
    public List<CitaResponse> listar(String emailActual) {
        Usuarios actual = usuarioActual(emailActual);
        List<Cita> citas = switch (actual.getRol()) {
            case ADMIN -> citaRepository.findAllByOrderByInicioDesc();
            case BARBERO -> actual.getBarbero() == null ? List.<Cita>of()
                    : citaRepository.findByBarberoIdOrderByInicioDesc(actual.getBarbero().getId());
            case CLIENTE -> actual.getCliente() == null ? List.<Cita>of()
                    : citaRepository.findByClienteIdOrderByInicioDesc(actual.getCliente().getId());
        };
        return citas.stream().map(this::toResponse).toList();
    }

    private Usuarios usuarioActual(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> error(HttpStatus.UNAUTHORIZED, "Sesión inválida"));
    }

    private ResponseStatusException error(HttpStatus status, String mensaje) {
        return new ResponseStatusException(status, mensaje);
    }

    private CitaResponse toResponse(Cita c) {
        return new CitaResponse(
                c.getId(),
                c.getCliente().getNombre() + " " + c.getCliente().getApellido(),
                c.getBarbero().getNombre() + " " + c.getBarbero().getApellido(),
                c.getServicio().getNombre(),
                c.getInicio(), c.getFin(), c.getEstado(), c.getPrecio(), c.getNotas());
    }
}
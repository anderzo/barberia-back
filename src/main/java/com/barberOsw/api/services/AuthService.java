package com.barberosw.api.services;

import com.barberosw.api.dto.RegistroRequest;
import com.barberosw.api.dto.UsuarioResponse;
import com.barberosw.api.models.Cliente;
import com.barberosw.api.models.Rol;
import com.barberosw.api.models.Usuarios;
import com.barberosw.api.repository.ClienteRepository;
import com.barberosw.api.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuariosRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmailIgnoreCase(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(req.nombre());
        cliente.setApellido(req.apellido());
        cliente.setEmail(req.email());
        cliente.setTelefono(req.telefono());
        clienteRepository.save(cliente);

        Usuarios usuario = new Usuarios();
        usuario.setEmail(req.email());
        usuario.setPasswordHash(passwordEncoder.encode(req.password()));
        usuario.setRol(Rol.CLIENTE);
        usuario.setCliente(cliente);
        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getId(), usuario.getEmail(), usuario.getRol());
    }
}
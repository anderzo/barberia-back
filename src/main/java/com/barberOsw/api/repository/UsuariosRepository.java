package com.barberosw.api.repository;

import com.barberosw.api.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
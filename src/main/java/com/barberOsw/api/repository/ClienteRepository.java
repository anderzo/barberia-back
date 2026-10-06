package com.barberosw.api.repository;

import com.barberosw.api.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmailIgnoreCase(String email);

    List<Cliente> findByActivoTrue(); 
}
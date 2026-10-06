package com.barberosw.api.repository;
import com.barberosw.api.models.Barbero;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {
    List<Barbero> findByActivoTrue();
}

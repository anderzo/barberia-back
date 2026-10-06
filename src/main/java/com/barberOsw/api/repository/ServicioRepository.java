package com.barberosw.api.repository;

import com.barberosw.api.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long>{
    List<Servicio> findByActivoTrue();
}

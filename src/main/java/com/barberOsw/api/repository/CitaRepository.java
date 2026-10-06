package com.barberosw.api.repository;
import com.barberosw.api.models.Cita;
import com.barberosw.api.models.EstadoCita;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long >{
    List<Cita> findAllByOrderByInicioDesc();
    List<Cita> findByClienteIdOrderByInicioDesc(Long clienteId);
    List<Cita> findByBarberoIdOrderByInicioDesc(Long barberoId);

@Query("""
    SELECT COUNT(c) > 0 FROM Cita c
    WHERE c.barbero.id = :barberoId AND c.estado <> :cancelada
      AND c.inicio < :fin AND c.fin > :inicio
    """)
boolean existeTraslape(Long barberoId, OffsetDateTime inicio, OffsetDateTime fin, EstadoCita cancelada);

}

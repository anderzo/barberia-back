package com.barberosw.api.repository;

import com.barberosw.api.models.HorarioBarbero;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HorarioBarberoRepository extends JpaRepository <HorarioBarbero, Long>{
    @Query("""
        SELECT COUNT(h) > 0 FROM HorarioBarbero h
        WHERE h.barbero.id = :barberoId AND h.diaSemana = :dia
        AND h.horaInicio <= :desde AND h.horaFin >= :hasta
        """)
    boolean cubreHorario(Long barberoId, Short dia, LocalTime desde, LocalTime hasta);
}
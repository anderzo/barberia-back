package com.barberosw.api.repository;
import com.barberosw.api.models.Barbero;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Long> {
    
}

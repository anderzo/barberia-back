package com.barberosw.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.HashSet;
import java.util.Set;

import java.time.OffsetDateTime;


@Entity
@Table(name = "barberos")
@Getter 
@Setter 
@NoArgsConstructor
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToMany
    @JoinTable(
        name = "barbero_servicios",
        joinColumns = @JoinColumn(name = "barbero_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private Set<Servicio> servicios = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
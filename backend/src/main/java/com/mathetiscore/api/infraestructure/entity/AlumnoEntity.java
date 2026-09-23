package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="alumnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "al_id", nullable = false)
    private UUID alId;

    @Column(name = "al_descripcion", columnDefinition = "TEXT")
    private String alDescripcion;

    @Column(name = "usuario_usu_id", nullable = false, unique = true)
    private UUID usuarioUsuId;
}

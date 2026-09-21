package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rol_id")
    private Long id;

    @Column(name = "rol_nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "rol_descripcion", columnDefinition = "TEXT")
    private String descripcion;
}

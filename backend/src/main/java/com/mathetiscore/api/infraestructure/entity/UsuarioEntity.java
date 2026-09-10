package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @Column(name = "usu_id", nullable = false)
    private UUID id;

    @Column(name = "usu_nombre", length = 100)
    private String nombre;

    @Column(name = "usu_apellido_paterno", length = 100)
    private String apellidoPaterno;

    @Column(name = "usu_apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "usu_email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "usu_rut", unique = true, length = 12)
    private String rut;

    @Column(name = "usu_descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_rol_id")
    private RoleEntity role;
}

package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//O generación pode defecto de UUID en Base de datos
    @Column(name = "prof_id", nullable = false)
    private UUID profId;

    @Column(name="pro_descripcion", columnDefinition = "TEXT")//Escribiendo esto me di cunta que la columna se llama pro_descripcion en lugar de prof_descripcion
    private String proDescripcion;

    @Column(name="usuario_usu_id", nullable = false, unique = true)
    private UUID usuarioId;

    @Column(name="asignacion_sede_ased_id", nullable = false)
    private UUID asedId;
}

package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "titulo_maestros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TituloMaestroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mapea el BIGINT GENERATED ALWAYS AS IDENTITY
    @Column(name = "tma_id")
    private Long tmaId;

    @Column(name = "tma_nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "tma_tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "tma_descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
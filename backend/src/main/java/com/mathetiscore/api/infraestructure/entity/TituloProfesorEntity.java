package com.mathetiscore.api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "titulos_profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TituloProfesorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tprof_id")
    private UUID id;

    @Column(name = "tprof_instituto_egreso")
    private String institutoEgreso;

    @Column(name = "tprof_anno_titulacion")
    private LocalDate annoTitulacion;

    @Column(name = "tprof_url_documento")
    private String urlDocumento;

    @Column(name = "tprof_descripcion")
    private String descripcion;

    // Llaves foraneas

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_prof_id", nullable = false)
    private ProfesorEntity profesor; // Guarda el ID del profesor recién creado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titulo_maestros_tma_id", nullable = false)
    private TituloMaestroEntity tituloMaestroEntity; // Guarda el ID del catálogo de títulos


}

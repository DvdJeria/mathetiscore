package com.mathetiscore.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TituloProfesor {

    private UUID id;
    private String institutoEgreso;
    private LocalDate annoTitulacion;
    private String urlDocumento;
    private String descripcion;

    // Referencias mediante IDs para mantener limpio el dominio
    private UUID profesorId;
    private Long tmaId;
}
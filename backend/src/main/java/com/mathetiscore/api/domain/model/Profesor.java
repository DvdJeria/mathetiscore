package com.mathetiscore.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    // Identificador único del profesor
    private UUID profId;

    // Descripción o biografía propia del profesor (en la entidad es pro_descripcion)
    private UUID usuarioId; // Relación con el usuario base

    // ID de la sede asignada (en la entidad es asignacion_sede_ased_id)
    private UUID asedId;

    // Descripción general o perfil del profesor
    private String descripcion;

    // --- Datos del Título Profesional (relacionados con titulos_profesor) ---
    private Long tmaId;             // ID del título maestro del catálogo
    private String institutoEgreso; // Dónde se tituló
    private String annoTitulacion;  // Año en que obtuvo el título
    private String urlDocumento;    // Enlace al documento/certificado adjunto
    private String tituloDescripcion; // Descripción específica del título (opcional)
}

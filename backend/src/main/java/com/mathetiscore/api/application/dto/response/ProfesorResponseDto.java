package com.mathetiscore.api.application.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesorResponseDto {
    // Datos del Usuario
    private UUID id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String rut;
    private String usuarioDescripcion;
    private String rol;

    // Datos del Profesor
    private UUID idProfesor;
    private String profesorDescripcion;
    private UUID asedId;

    // Datos del Título Profesional
    private long tmaId;
    private String institucionEgreso;
    private String annoTitulacion;
    private String urlDocumento;
    private String tituloDescripcion;
}

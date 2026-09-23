package com.mathetiscore.api.application.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoResponseDto {

    private UUID alumnoId;
    private UUID usuarioId;
    private String rut;
    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String mensaje; //(?)
}

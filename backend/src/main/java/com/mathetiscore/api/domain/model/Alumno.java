package com.mathetiscore.api.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    private UUID alId;
    private String alDescripcion;
    private UUID usuarioUsuId;

    //Variables vinculadas a la tabla usuario
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password;
    private String rut;
    private String usuDescripcion;
}

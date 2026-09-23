package com.mathetiscore.api.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoRequestDto {

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password;
    private String rut;
    private String usuarioDescripcion;

    //Atributo específico de la tabla alumno
    private String alDescripcion;
}

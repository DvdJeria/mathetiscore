package com.mathetiscore.api.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorRequestDto {

    // Datos para la tabla usuario
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password;
    private String rut;
    private String usuarioDescripcion;

    // Datos para la tabla profesor
    private UUID asedId;
    private String profesorDescripcion;

    // Datos para la tabla 'titulos_profesor'
    private long tmaId;
    private String institutoEgreso;
    private LocalDate annoTitulacion;
    private String urlDocumento;
    private String tituloDescripcion;
}

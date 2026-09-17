package com.mathetiscore.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    // Campos base del usuario
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password; //Requerido para inicializar la cuenta en Supabase Auth
    private String rut;
    private String descripcion;

    //Identificador del Rol
    private Integer rolId; //2: Admin Sede, 3: Docnte, 4: Estudiante (según catalógo)

    private UUID asedId; //Usado si es Docente o Estudiante (Asignación de Sede)
    private Long tmaId; //Usado exclusivament si es Docente (Titulo Maestro)

}
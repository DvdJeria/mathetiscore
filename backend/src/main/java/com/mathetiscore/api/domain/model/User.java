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
public class User {
    private UUID id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String rut;
    private String descripcion;
    private Role role;

    public String getNombreCompleto(){
        StringBuilder sb = new StringBuilder();
        if(nombre != null) sb.append(nombre);
        if(apellidoPaterno != null) sb.append(apellidoPaterno);
        if(apellidoMaterno != null) sb.append(apellidoMaterno);
        return sb.toString().trim();
    }

    public void roleId() {

    }
}

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
public class Alumno {
    private UUID id;
    private String descripcion;
    private UUID usuarioId;
}

package com.mathetiscore.api.domain.port;

import com.mathetiscore.api.domain.model.Alumno;

import java.util.Optional;
import java.util.UUID;

public interface AlumnoRepositoryPort {
    Alumno save (Alumno alumno);
    Optional<Alumno> findById(UUID id);
}

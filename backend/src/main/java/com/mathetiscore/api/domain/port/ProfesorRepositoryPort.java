package com.mathetiscore.api.domain.port;

import com.mathetiscore.api.domain.model.Profesor;

import java.util.Optional;
import java.util.UUID;

public interface ProfesorRepositoryPort {
    Profesor save(Profesor profesor);
    Optional<Profesor> findById(UUID id);
}

package com.mathetiscore.api.domain.port;


import com.mathetiscore.api.domain.model.Profesor;

public interface ProfesorRepositoryPort {
    Profesor save(Profesor profesor);
}

package com.mathetiscore.api.infraestructure.repository.adapter;

import com.mathetiscore.api.infraestructure.entity.AlumnoEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.AlumnoSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlumnoRepositoryAdapter {

    private final AlumnoSpringDataRepository alumnoSpringDataRepository;


    public void save(UUID alId, UUID usuarioUsuId){
        AlumnoEntity entity = new AlumnoEntity();
        entity.setAlId(alId);
        entity.setUsuarioUsuId(usuarioUsuId);

        alumnoSpringDataRepository.save(entity);
    }
}

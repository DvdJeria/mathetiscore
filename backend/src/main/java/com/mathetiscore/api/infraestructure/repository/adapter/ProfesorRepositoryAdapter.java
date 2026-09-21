package com.mathetiscore.api.infraestructure.repository.adapter;
import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.ProfesorSpringDataRepository;
import com.mathetiscore.api.infraestructure.repository.mapper.ProfesorPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProfesorRepositoryAdapter implements ProfesorRepositoryPort{

    private final ProfesorSpringDataRepository profesorSpringDataRepository;
    private final ProfesorPersistenceMapper profesorPersistenceMapper;

    @Override
    public Profesor save(Profesor profesor){
        // 1. Convertir el modelo de dominio en Entidad JPA
        ProfesorEntity entity = profesorPersistenceMapper.toEntity(profesor);

        //Guardar usando Spring Data JPA
        ProfesorEntity savedEntity = profesorSpringDataRepository.save(entity);

        //3. Convertir la entidad  guardada de vuelta a modelo de dominio y retornarla
        return profesorPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Profesor> findById(UUID id){
        return profesorSpringDataRepository.findById(id)
                .map(profesorPersistenceMapper::toDomain);
    }

}

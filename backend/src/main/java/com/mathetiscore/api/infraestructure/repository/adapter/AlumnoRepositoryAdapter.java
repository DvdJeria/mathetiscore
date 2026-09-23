package com.mathetiscore.api.infraestructure.repository.adapter;

import com.mathetiscore.api.domain.model.Alumno;
import com.mathetiscore.api.domain.port.AlumnoRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.AlumnoEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.AlumnoSpringDataRepository;
import com.mathetiscore.api.infraestructure.repository.mapper.AlumnoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlumnoRepositoryAdapter implements AlumnoRepositoryPort {

    private final AlumnoSpringDataRepository alumnoSpringDataRepository;
    private final AlumnoPersistenceMapper alumnoPersistenceMapper;


    @Override
    public Alumno save(Alumno alumno){
        //1. Convertir el modelo de dominio en entidad JPA
        AlumnoEntity alumnoEntity = alumnoPersistenceMapper.toEntity(alumno);

        //2. Guardar en la base de datos usando JPA
        AlumnoEntity savedEntity = alumnoSpringDataRepository.save(alumnoEntity);

        return alumnoPersistenceMapper.toDomain(savedEntity);

    }

    @Override
    public Optional<Alumno> findById(UUID alumnoId){
        return alumnoSpringDataRepository.findById(alumnoId)
                .map(alumnoPersistenceMapper::toDomain);
    }
}

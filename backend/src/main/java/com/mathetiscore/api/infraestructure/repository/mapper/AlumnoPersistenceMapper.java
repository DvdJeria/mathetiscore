package com.mathetiscore.api.infraestructure.repository.mapper;

import com.mathetiscore.api.domain.model.Alumno;
import com.mathetiscore.api.infraestructure.entity.AlumnoEntity;
import org.springframework.stereotype.Component;

@Component
public class AlumnoPersistenceMapper {

    public AlumnoEntity toEntity(Alumno alumno){
        if(alumno == null){
            return null;
        }
        AlumnoEntity alumnoEntity = new AlumnoEntity();
        alumnoEntity.setAlId(alumno.getAlId());
        alumnoEntity.setUsuarioUsuId(alumno.getUsuarioUsuId());
        alumnoEntity.setAlDescripcion(alumno.getAlDescripcion());

        return alumnoEntity;

    }

    public Alumno toDomain(AlumnoEntity alumnoEntity){
        if(alumnoEntity == null){
            return null;
        }
        return Alumno.builder()
                .alId(alumnoEntity.getAlId())
                .alDescripcion(alumnoEntity.getAlDescripcion())
                .usuarioUsuId(alumnoEntity.getUsuarioUsuId())
                .build();

    }
}

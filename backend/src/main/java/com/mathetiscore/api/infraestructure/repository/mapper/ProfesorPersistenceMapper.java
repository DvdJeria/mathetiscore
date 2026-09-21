package com.mathetiscore.api.infraestructure.repository.mapper;

import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfesorPersistenceMapper {

    public Profesor toDomain(ProfesorEntity entity) {
        if (entity == null) return null;

        Profesor profesor = new Profesor();
        profesor.setProfId(entity.getProfId());
        profesor.setDescripcion(entity.getProDescripcion());
        profesor.setUsuarioId(entity.getUsuarioId());
        profesor.setAsedId(entity.getAsedId());
        return profesor;
    }

    public ProfesorEntity toEntity(Profesor domain) {
        if (domain == null) return null;

        ProfesorEntity entity = new ProfesorEntity();
        entity.setProfId(domain.getProfId());
        entity.setProDescripcion(domain.getDescripcion());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setAsedId(domain.getAsedId());
        return entity;
    }
}

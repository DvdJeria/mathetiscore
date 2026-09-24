package com.mathetiscore.api.infraestructure.repository.adapter;

import com.mathetiscore.api.domain.model.TituloProfesor;
import com.mathetiscore.api.domain.port.TituloProfesorRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
import com.mathetiscore.api.infraestructure.entity.TituloMaestroEntity;
import com.mathetiscore.api.infraestructure.entity.TituloProfesorEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.TituloProfesorSpringDataRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TituloProfesorRepositoryAdapter implements TituloProfesorRepositoryPort {

    private final TituloProfesorSpringDataRepository repository;
    private final EntityManager entityManager;

    @Override
    public TituloProfesor save(TituloProfesor dominio){


        ProfesorEntity profesorEntity = entityManager.getReference(ProfesorEntity.class, dominio.getProfesorId());
        profesorEntity.setProfId(dominio.getProfesorId());

        TituloMaestroEntity maestroEntity = entityManager.getReference(TituloMaestroEntity.class, dominio.getTmaId());
        maestroEntity.setTmaId(dominio.getTmaId());

        TituloProfesorEntity entity = TituloProfesorEntity.builder()
                .institutoEgreso(dominio.getInstitutoEgreso())
                .annoTitulacion(dominio.getAnnoTitulacion())
                .urlDocumento(dominio.getUrlDocumento())
                .descripcion(dominio.getDescripcion())
                .profesor(profesorEntity)
                .tituloMaestroEntity(maestroEntity)
                .build();

                TituloProfesorEntity saved = repository.save(entity);
                dominio.setId(saved.getId());
                return dominio;
    }
}

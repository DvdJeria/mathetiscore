package com.mathetiscore.api.infraestructure.repository.adapter;

import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
//import com.mathetiscore.api.infraestructure.repository.jpa.
import com.mathetiscore.api.infraestructure.repository.jpa.ProfesorSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfesorRepositoryAdapter {
    private final ProfesorSpringDataRepository profesorSpringDataRepository;

    public void save(UUID usuarioId, Long tmaId, UUID asedId){
        ProfesorEntity entity = new ProfesorEntity();
        entity.setUsuarioId(usuarioId);
        entity.setAsedId(asedId);

        profesorSpringDataRepository.save(entity);
    }
}

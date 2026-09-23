package com.mathetiscore.api.infraestructure.repository.adapter;
import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
import com.mathetiscore.api.infraestructure.entity.TituloMaestroEntity;
import com.mathetiscore.api.infraestructure.entity.TituloProfesorEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.ProfesorSpringDataRepository;
import com.mathetiscore.api.infraestructure.repository.jpa.TituloProfesorSpringDataRepository;
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
    private final TituloProfesorSpringDataRepository tituloProfesorSpringDataRepository;

    @Override
    public Profesor save(Profesor profesor){
        // 1. Convertir el modelo de dominio en Entidad JPA
        ProfesorEntity entity = profesorPersistenceMapper.toEntity(profesor);

        //Guardar usando Spring Data JPA
        ProfesorEntity savedEntity = profesorSpringDataRepository.save(entity);

        //3. Guardar el registro de tmaid en la tabla titulo_profesor
        if (profesor.getTmaId() != null) {
            TituloProfesorEntity tituloProfesorEntity = new TituloProfesorEntity();
            tituloProfesorEntity.setId(savedEntity.getProfId());

            tituloProfesorEntity.setProfesor(savedEntity);

            //Instanciar TituloMaestro para la inserción del ID en el objeto
            TituloMaestroEntity tituloMaestro = new TituloMaestroEntity();
            tituloMaestro.setTmaId(profesor.getTmaId()); //<- Asignar id del objeto guardado en el id del objeto de título maestro
            tituloProfesorEntity.setTituloMaestroEntity(tituloMaestro); //<- Recibe parámetro como objeto

            tituloProfesorEntity.setInstitutoEgreso(profesor.getInstitutoEgreso());
            tituloProfesorEntity.setAnnoTitulacion(profesor.getAnnoTitulacion());
            tituloProfesorEntity.setUrlDocumento(profesor.getUrlDocumento());
            tituloProfesorEntity.setDescripcion(profesor.getDescripcion());

            tituloProfesorSpringDataRepository.save(tituloProfesorEntity);

        }

        //4. Convertir la entidad guardada de vuelta a modelo de dominio y retornarla
        return profesorPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Profesor> findById(UUID id){
        return profesorSpringDataRepository.findById(id)
                .map(profesorPersistenceMapper::toDomain);
    }

}

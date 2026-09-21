package com.mathetiscore.api.infraestructure.repository.jpa;

import com.mathetiscore.api.infraestructure.entity.TituloProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TituloProfesorSpringDataRepository extends JpaRepository<TituloProfesorEntity, UUID> {

}

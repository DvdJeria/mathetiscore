package com.mathetiscore.api.infraestructure.repository.jpa;

import com.mathetiscore.api.infraestructure.entity.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlumnoSpringDataRepository extends JpaRepository<AlumnoEntity, UUID> {
}

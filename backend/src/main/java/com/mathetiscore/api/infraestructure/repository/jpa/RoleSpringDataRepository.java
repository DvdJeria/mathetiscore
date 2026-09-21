package com.mathetiscore.api.infraestructure.repository.jpa;

import com.mathetiscore.api.infraestructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleSpringDataRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByNombre(String nombre);
}

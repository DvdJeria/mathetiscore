package com.mathetiscore.api.infraestructure.repository.jpa;

import com.mathetiscore.api.infraestructure.entity.ProfesorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ProfesorSpringDataRepository extends JpaRepository<ProfesorEntity, UUID> {
}

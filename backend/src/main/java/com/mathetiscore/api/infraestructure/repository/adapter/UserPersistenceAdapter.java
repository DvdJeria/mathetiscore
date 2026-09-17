package com.mathetiscore.api.infraestructure.repository.adapter;

import java.util.Optional;
import java.util.UUID;

import com.mathetiscore.api.infraestructure.repository.mapper.UserPersistenceMapper;
import com.mathetiscore.api.infraestructure.repository.jpa.UserSpringDataRepository;
import org.springframework.stereotype.Component;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserSpringDataRepository springDataRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public Optional<User> finById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UsuarioEntity entityToSave = mapper.toEntity(user);
        UsuarioEntity savedEntity = springDataRepository.save(entityToSave);
        return mapper.toDomain(savedEntity);
    }
}
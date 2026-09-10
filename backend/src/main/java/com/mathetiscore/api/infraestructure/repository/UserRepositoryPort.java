package com.mathetiscore.api.infraestructure.repository;

import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.infraestructure.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryPort {

    private final UserSpringDataRepository userSpringDataRepository;
    private final UserPersistenceMapper mapper;


    public Optional<User> finById(UUID id){
        return userSpringDataRepository.findById(id)
                .map(mapper::toDomain);
    }


    public Optional<User> findByEmail(String email){
        return userSpringDataRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    public User save(User user){
        UsuarioEntity entityToSave = mapper.toEntity(user);
        UsuarioEntity savedEntity = userSpringDataRepository.save(entityToSave);
        return mapper.toDomain(savedEntity);
    }
}

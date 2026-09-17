package com.mathetiscore.api.infraestructure.repository.mapper;

import com.mathetiscore.api.domain.model.Role;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.infraestructure.entity.RoleEntity;
import com.mathetiscore.api.infraestructure.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UsuarioEntity entity){
        if(entity == null) return null;

        Role role = null;
        if(entity.getRole() != null){
            role = new Role(
                    entity.getRole().getId(),
                    entity.getRole().getNombre(),
                    entity.getRole().getDescripcion()
            );
        }

        return new User(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getEmail(),
                entity.getRut(),
                entity.getDescripcion(),
                role
        );
    }

    public UsuarioEntity toEntity(User domain){
        if(domain == null) return null;

        RoleEntity roleEntity = null;
        if(domain.getRole() != null){
            roleEntity = new RoleEntity(
                    domain.getRole().getId(),
                    domain.getRole().getNombre(),
                    domain.getRole().getDescripcion()
            );
        }

        return new UsuarioEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getApellidoPaterno(),
                domain.getApellidoMaterno(),
                domain.getEmail(),
                domain.getRut(),
                domain.getDescripcion(),
                roleEntity
        );
    }
}

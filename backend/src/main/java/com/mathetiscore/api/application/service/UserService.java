package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.UserResponseDto;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final MapperBuilder mapperBuilder;

    public UserResponseDto getUserById(UUID id){
        User user = userRepositoryPort.finById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return mapToDo(user);
    }

    public UserResponseDto getUserByEmail(String email){
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));

        return mapToDo(user);
    }

    private UserResponseDto mapToDo(User user){
        String rolNombre = (user.getRole() != null) ? user.getRole().getNombre() : "SIN_ROL";

        return UserResponseDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidoPaterno(user.getApellidoPaterno())
                .apellidoMaterno(user.getApellidoMaterno())
                .email(user.getEmail())
                .rut(user.getRut())
                .rolNombre(rolNombre)
                .build();
    }
}

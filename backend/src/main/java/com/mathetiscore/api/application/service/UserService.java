package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.request.ProfesorRequestDto;
import com.mathetiscore.api.application.dto.response.ProfesorResponseDto;
import com.mathetiscore.api.application.dto.response.UserResponseDto;
import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.model.Role;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import com.mathetiscore.api.infraestructure.entity.RoleEntity;
import com.mathetiscore.api.infraestructure.entity.TituloMaestroEntity;
import com.mathetiscore.api.infraestructure.entity.TituloProfesorEntity;
import com.mathetiscore.api.infraestructure.repository.jpa.RoleSpringDataRepository;
import com.mathetiscore.api.infraestructure.repository.jpa.TituloMaestroSpringDataRepository;
import com.mathetiscore.api.infraestructure.repository.jpa.TituloProfesorSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final RoleSpringDataRepository roleSpringDataRepository;
    private final TituloProfesorSpringDataRepository tituloProfesorSpringDataRepository;
    private final TituloMaestroSpringDataRepository tituloMaestroSpringDataRepository;
    //private final MapperBuilder mapperBuilder;

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

    public ProfesorResponseDto createProfesor(ProfesorRequestDto dto){
        return null;
    }
}

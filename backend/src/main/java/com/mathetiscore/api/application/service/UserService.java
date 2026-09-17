package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.UserRequestDto;
import com.mathetiscore.api.application.dto.UserResponseDto;
import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.model.Role;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.AlumnoRepositoryPort;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final MapperBuilder mapperBuilder;

    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final AlumnoRepositoryPort alumnoRepositoryPort;

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

    @Transactional
    public UserResponseDto createUser(UserRequestDto request){
        //Primero hay que validar si el correo existe
        if(userRepositoryPort.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("El correo ya existe");
        }

        //2. Mapear el Request DTO al modelo de Dominio (Usr)
        //Nota: El ID se generará automáticamente (UUID) por Supabase
        User newUser = new User();
        newUser.setNombre(request.getNombre());
        newUser.setApellidoPaterno(request.getApellidoPaterno());
        newUser.setApellidoMaterno(request.getApellidoMaterno());
        newUser.setEmail(request.getEmail());
        newUser.setRut(request.getRut());
        newUser.setDescripcion(request.getDescripcion());

        //Asignar el rol según el ID recibido
        Role role = new Role();
        role.setId(request.getRolId().longValue());
        newUser.setRole(role);

        //3. Guarda el usuario base a través del puerto
        User savedUser = userRepositoryPort.save(newUser);

        switch (request.getRolId()){
            case 2:
                break;
            case 3:
                Profesor profesor = new Profesor();
                profesor.setId(savedUser.getId());
                profesor.setAsedId(request.getAsedId());
                profesor.setDescripcion(request.getDescripcion());


                break;
            case 4:
                break;
            default:
                throw new IllegalArgumentException("Rol no válido");
        }

        return mapToDo(savedUser);

    }

}

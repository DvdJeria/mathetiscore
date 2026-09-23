package com.mathetiscore.api.application.service;

import com.mathetiscore.api.domain.model.Role;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRegistrationHelper {

    private final AuthService authService;
    private final UserRepositoryPort userRepositoryPort;

    public User createBaseUser(String nombre, String apellidoPaterno, String apellidoMaterno,
                               String email, String rut, String password, Long roleId){

        //1. Insertamos los datos en la tabla auth.users
        UUID authUuid = authService.registerUserCredentials(email, password);

        //2. Busca el registro que el trigger creo automáticamente en la tabla local Usuario
        User usuario = userRepositoryPort.findById(authUuid)
                .orElseThrow(() -> new RuntimeException("Error crítico: Supabase no creo registro de id: "+authUuid));

        //3. Actualizar los valores de la tabla usuario
        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPaterno);
        usuario.setApellidoMaterno(apellidoMaterno);
        usuario.setRut(rut);
        usuario.setEmail(email);

        Role role = new Role();
        role.setId(roleId);

        return userRepositoryPort.save(usuario);
    }
}

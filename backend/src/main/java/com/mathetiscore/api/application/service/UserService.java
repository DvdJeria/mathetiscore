package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.response.UserResponseDto;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.domain.port.UserRepositoryPort;
import com.mathetiscore.api.infraestructure.repository.jpa.TituloProfesorSpringDataRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final AuthService authService;
    private final UserRepositoryPort userRepositoryPort;
    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final TituloProfesorSpringDataRepository tituloProfesorSpringDataRepository;

    public UserService(AuthService authService,
                       UserRepositoryPort usuarioRepository,
                       ProfesorRepositoryPort profesorRepository,
                       TituloProfesorSpringDataRepository tituloProfesorRepository) {
        this.authService = authService;
        this.userRepositoryPort = usuarioRepository;
        this.profesorRepositoryPort = profesorRepository;
        this.tituloProfesorSpringDataRepository = tituloProfesorRepository;
    }

    public UserResponseDto getUserById(UUID id){
        User user = userRepositoryPort.findById(id)
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
/*
*
* @Transactional
    public ProfesorResponseDto createProfesor(ProfesorRequestDto dto) {

        //Insertar el correo y contraseña que vienen del request, al método aislado del servicio
        UUID authUuid = authService.registerUserCredentials(dto.getEmail(), dto.getPassword());

        //Buscar el UUID creado por el trigger y la inserción en la tabla usuario
        User usuario = userRepositoryPort.findById(authUuid)
                .orElseThrow(() -> new RuntimeException("Error critico: Supabase no genero el usuario local para el UUID: "+ authUuid));

        //Actualizar, no insertar, datos en tabla 'usuario'.
        usuario.setNombre(dto.getNombre());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setEmail(dto.getEmail());
        usuario.setRut(dto.getRut());

        //Construir el rol para la asignación en el objeto
        Role rol = new Role();
        rol.setId(3L);
        usuario.setRole(rol);//<- Asignación del rol en la instancia de objeto

        userRepositoryPort.save(usuario);

        //Crear e insertar los datos específicos en la tabla profesor
        Profesor profesor = new  Profesor();
        profesor.setUsuarioId(authUuid); //Inserto directamente el authUuid, porque lo rescata del sistema y no del request
        profesor.setAsedId(dto.getAsedId());

        //Insertar datos en tabla titulos_profesor si el request los trae
        if (dto.getTmaId() != null){
            profesor.setTmaId(dto.getTmaId());
            profesor.setInstitutoEgreso(dto.getInstitutoEgreso());
            profesor.setAnnoTitulacion(dto.getAnnoTitulacion());
            profesor.setUrlDocumento(dto.getUrlDocumento());
            profesor.setDescripcion(dto.getTituloDescripcion());


        }

        profesorRepositoryPort.save(profesor);

        return ProfesorResponseDto.builder()
                .id(authUuid)
                .nombre(usuario.getNombre())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(usuario.getEmail())
                .rut(usuario.getRut())
                .build();
    }
*/

}

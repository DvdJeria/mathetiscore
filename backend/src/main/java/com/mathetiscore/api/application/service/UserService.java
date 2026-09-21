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

        //1. Buscar el rol Profesor usando el JPA existente
        RoleEntity roleEntity = roleSpringDataRepository.findByNombre("PROFESOR")
                .orElseThrow(() -> new RuntimeException("El rol Profesor no existe"));

        Role roleDomain = new Role(
                roleEntity.getId(),
                roleEntity.getNombre(),
                roleEntity.getDescripcion()
        );

        //2. Mapear y guardar el usuario base usando el puerto UserRepositoryport
        User userDomain = new User(
                null,
                dto.getNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getUsuarioDescripcion(),
                roleDomain
        );
        User savedUser = userRepositoryPort.save(userDomain);

        //3. Crear y guardar el registro en la tabla "Profesor" usando ProfesorRepositoryPort
        Profesor profesorDomain = new Profesor();
                profesorDomain.setProfId(null);
                profesorDomain.setDescripcion(dto.getProfesorDescripcion());
                profesorDomain.setUsuarioId(savedUser.getId());
                profesorDomain.setAsedId(dto.getAsedId());
                profesorDomain.setTmaId(dto.getTmaId());
        Profesor savedProfesor = profesorRepositoryPort.save(profesorDomain);

        String annoStr = null;
        //4. Guardar el título profesional en "titulos_profesor"
        if(dto.getTmaId() > 0){

            TituloMaestroEntity tituloMaestroEntity = tituloMaestroSpringDataRepository.findById(dto.getTmaId())
                    .orElseThrow(() -> new RuntimeException("El título maestro con ID " + dto.getTmaId() + " no fue encontrado"));

            TituloProfesorEntity tituloEntity = new TituloProfesorEntity();

            tituloEntity.setId(savedProfesor.getProfId());
            tituloEntity.setTituloMaestroEntity(tituloMaestroEntity);
            tituloEntity.setInstitutoEgreso(dto.getInstitutoEgreso());

            //Conversión obligatorio de LocalDate a String

            if(dto.getAnnoTitulacion() != null){
                annoStr = String.valueOf(dto.getAnnoTitulacion().getYear());
            }
            tituloEntity.setAnnoTitulacion(annoStr);
            tituloEntity.setUrlDocumento(dto.getUrlDocumento());
            tituloEntity.setDescripcion(dto.getTituloDescripcion());

            tituloProfesorSpringDataRepository.save(tituloEntity);


        }

        //5. Retornar el DTO completo de respuesta con toda la estructura
        return new ProfesorResponseDto(
                savedUser.getId(),
                savedUser.getNombre(),
                savedUser.getApellidoPaterno(),
                savedUser.getApellidoMaterno(),
                savedUser.getEmail(),
                savedUser.getRut(),
                savedUser.getDescripcion(),
                roleDomain.getNombre(),

                savedProfesor.getProfId(),
                savedProfesor.getDescripcion(),
                savedProfesor.getAsedId(),

                dto.getTmaId(),
                dto.getInstitutoEgreso(),
                annoStr,
                dto.getUrlDocumento(),
                dto.getTituloDescripcion()
        );

    }

    /*
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
    */



}

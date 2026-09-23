package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.request.AlumnoRequestDto;
import com.mathetiscore.api.application.dto.response.AlumnoResponseDto;
import com.mathetiscore.api.domain.model.Alumno;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.AlumnoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final UserRegistrationHelper userRegistrationHelper;
    private final AlumnoRepositoryPort alumnoRepositoryPort;

    private static final Long ROL_ALUMNO_ID =  4L; //Asignación del rol directo desde variable

    @Transactional
    public AlumnoResponseDto createAlumno(AlumnoRequestDto dto) {

        //Creación del usuario paa pasar por controlador del método helper, datos desde el request
        User usuarioCreado = userRegistrationHelper.createBaseUser(
                dto.getNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getEmail(),
                dto.getRut(),
                dto.getPassword(),
                ROL_ALUMNO_ID //<- Le asignamos el rol correspondiente al usuario a crear.
        );
        System.out.println("ID del usuario creado: "+ (usuarioCreado != null ? usuarioCreado.getId() : "ES NULL"));

        //Estructuramos el objeto de Alumno para insertar en la tabla
        Alumno alumno = Alumno.builder()
                //.alId(usuarioCreado.getId())
                .usuarioUsuId(usuarioCreado.getId())
                .alDescripcion(dto.getAlDescripcion())
                .build();

        Alumno alumnoGuardado = alumnoRepositoryPort.save(alumno);

        //Estructuramos el responde para devolver al frontend
        return AlumnoResponseDto.builder()
                .alumnoId(alumnoGuardado.getAlId())
                .usuarioId(usuarioCreado.getId())
                .nombre(usuarioCreado.getNombre())
                .apellidoPaterno(usuarioCreado.getApellidoPaterno())
                .apellidoMaterno(usuarioCreado.getApellidoMaterno())
                .email(usuarioCreado.getEmail())
                .rut(usuarioCreado.getRut())
                .mensaje("Alumno creado exitosamente")
                .build();
    }

}

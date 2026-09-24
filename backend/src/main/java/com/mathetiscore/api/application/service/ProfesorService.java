package com.mathetiscore.api.application.service;

import com.mathetiscore.api.application.dto.request.ProfesorRequestDto;
import com.mathetiscore.api.application.dto.response.ProfesorResponseDto;
import com.mathetiscore.api.domain.model.Profesor;
import com.mathetiscore.api.domain.model.TituloProfesor;
import com.mathetiscore.api.domain.model.User;
import com.mathetiscore.api.domain.port.ProfesorRepositoryPort;
import com.mathetiscore.api.domain.port.TituloProfesorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final UserRegistrationHelper userRegistrationHelper;
    private final ProfesorRepositoryPort profesorRepositoryPort;
    private final TituloProfesorRepositoryPort  tituloProfesorRepositoryPort;

    private static final Long ROL_PROFESOR_ID = 3L;

    @Transactional
    public ProfesorResponseDto createProfesor(ProfesorRequestDto dto) {

        //Creación del objeto para las modificaciones de la tabla usuario
        User usuarioCreado = userRegistrationHelper.createBaseUser(
                dto.getNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getEmail(),
                dto.getRut(),
                dto.getPassword(),
                ROL_PROFESOR_ID
        );

        //Creación de objeto para inserción en tabla profesor
        Profesor profesor = new Profesor();
        profesor.setUsuarioId(usuarioCreado.getId());
        profesor.setAsedId(dto.getAsedId());

        Profesor profesorGuardado = profesorRepositoryPort.save(profesor);

        //Validar request de título para insertar datos
        if (dto.getTmaId() != null) {
            TituloProfesor titulo = TituloProfesor.builder()
                    .profesorId(profesorGuardado.getProfId())
                    .tmaId(dto.getTmaId())
                    .institutoEgreso(dto.getInstitutoEgreso())
                    .annoTitulacion(dto.getAnnoTitulacion()) // Asegúrate de transformar String a LocalDate si tu DTO lo recibe como texto
                    .urlDocumento(dto.getUrlDocumento())
                    .descripcion(dto.getTituloDescripcion())
                    .build();

            tituloProfesorRepositoryPort.save(titulo);
        }

        //profesorRepositoryPort.save(profesor);

        return ProfesorResponseDto.builder()
                .id(usuarioCreado.getId())
                .nombre(usuarioCreado.getNombre())
                .apellidoPaterno(usuarioCreado.getApellidoPaterno())
                .apellidoMaterno(usuarioCreado.getApellidoMaterno())
                .email(usuarioCreado.getEmail())
                .rut(usuarioCreado.getRut())
                .build();
    }

}

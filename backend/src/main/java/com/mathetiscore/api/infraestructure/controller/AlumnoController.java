package com.mathetiscore.api.infraestructure.controller;

import com.mathetiscore.api.application.dto.request.AlumnoRequestDto;
import com.mathetiscore.api.application.dto.response.AlumnoResponseDto;
import com.mathetiscore.api.application.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<AlumnoResponseDto> createAlumno(@Validated @RequestBody AlumnoRequestDto dto) {
        AlumnoResponseDto alumnoResponseDto = alumnoService.createAlumno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoResponseDto);
    }
}

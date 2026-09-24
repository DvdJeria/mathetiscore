package com.mathetiscore.api.infraestructure.controller;

import com.mathetiscore.api.application.dto.request.ProfesorRequestDto;
import com.mathetiscore.api.application.dto.response.ProfesorResponseDto;
import com.mathetiscore.api.application.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profesor")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService  profesorService;

    @PostMapping
    public ResponseEntity<ProfesorResponseDto> createProfesor(@Validated @RequestBody ProfesorRequestDto dto) {
        ProfesorResponseDto response = profesorService.createProfesor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

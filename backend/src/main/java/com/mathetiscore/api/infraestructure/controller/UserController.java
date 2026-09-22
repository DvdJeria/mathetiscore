package com.mathetiscore.api.infraestructure.controller;

import com.mathetiscore.api.application.dto.request.ProfesorRequestDto;
import com.mathetiscore.api.application.dto.response.ProfesorResponseDto;
import com.mathetiscore.api.application.dto.response.UserResponseDto;
import com.mathetiscore.api.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Endpoint especializado en Profesores
    @PostMapping("/profesor")
    public ResponseEntity<ProfesorResponseDto> createProfesor(@Validated @RequestBody ProfesorRequestDto dto) {
        // El servicio ahora retorna un ProfesorResponseDto
        ProfesorResponseDto response = userService.createProfesor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

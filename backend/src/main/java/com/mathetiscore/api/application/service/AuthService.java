package com.mathetiscore.api.application.service;

import com.mathetiscore.api.domain.port.outbound.AuthPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final AuthPort authPort;

    public AuthService(AuthPort authPort) {
        this.authPort = authPort;
    }

    /*
    * Método reutilizable y aislado para registrar credenciales en Supabase Auth
    *    y retornar el UUID generado
    */
    public UUID registerUserCredentials(String email, String password){
        return authPort.registerUserInAuth(email, password);
    }
}

package com.mathetiscore.api.infraestructure.repository.adapter;

import com.mathetiscore.api.domain.port.outbound.AuthPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SupabaseAuthRepositoryAdapter implements AuthPort {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service-role-key}")
    private String serviceRoleKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UUID registerUserInAuth(String email, String Password) {
        String url = supabaseUrl + "/auth/v1/admin/users";

        //Configurar cabeceras para la api admin de Supabase
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", serviceRoleKey);
        headers.set("Authorization", "Bearer " + serviceRoleKey);

        //Cuerpo de la petición con las credenciales
        Map<String , Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", Password);
        body.put("email_confirm", true);

        HttpEntity<Map<String , Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                Object idObj =  responseBody.get("id");
                if(idObj != null && responseBody.containsKey("user")) {
                    Map<String, Object> userMap = (Map<String, Object>) responseBody.get("user");
                    idObj = userMap.get("id");
                }

                if(idObj != null) {
                    return UUID.fromString(idObj.toString());
                }
            }
            throw new RuntimeException("Error: La respuesta de Supabase Auth no devolvió un UUID válido.");
        }catch (Exception e) {
            throw new RuntimeException("Fallo al registrar credenciales en Supabase Auth: " + e.getMessage(), e);
        }
    }
}

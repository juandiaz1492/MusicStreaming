package com.musicstreaming.servicios;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.musicstreaming.dto.LoginRequest;

import org.springframework.http.HttpStatus;

@Service
public class KeyCloakLoginService {

    private final WebClient webClient;

    @Value("${keycloak.base-url}")
    private String baseUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret:}")
    private String clientSecret;

    public KeyCloakLoginService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String login(LoginRequest request) {

        String tokenUrl = baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("username", request.username());
        form.add("password", request.password());

        if (clientSecret != null && !clientSecret.isBlank()) {
            form.add("client_secret", clientSecret);
        }

        Map<String, Object> response = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .onStatus(status -> status.value() == 400 || status.value() == 401,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(msg -> new ResponseStatusException(
                                        HttpStatus.UNAUTHORIZED,
                                        "Credenciales inválidas"
                                ))
                )
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return (String) response.get("access_token");
    }
}

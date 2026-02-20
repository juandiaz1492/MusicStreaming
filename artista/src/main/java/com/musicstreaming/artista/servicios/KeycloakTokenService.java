package com.musicstreaming.artista.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class KeycloakTokenService {

    private final RestClient rest;

    @Value("${keycloak.base-url}") private String baseUrl;
    @Value("${keycloak.realm}") private String realm;
    @Value("${keycloak.client-id}") private String clientId;
    @Value("${keycloak.client-secret}") private String clientSecret;

    private String cachedToken;
    private long expiresAt; // epoch seconds

    public KeycloakTokenService(RestClient.Builder builder) {
        this.rest = builder.build();
    }

    @SuppressWarnings("null")
    public synchronized String getToken() {
        long now = System.currentTimeMillis() / 1000;

        if (cachedToken != null && now < expiresAt - 30) {
            return cachedToken;
        }

        String tokenUrl = baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        JsonNode resp = rest.post()
                .uri(tokenUrl)
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(JsonNode.class);

        cachedToken = resp.get("access_token").asText();
        expiresAt = now + resp.get("expires_in").asLong();

        return cachedToken;
    }
}
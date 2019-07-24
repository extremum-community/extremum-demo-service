package com.cybernation.testservice;

import com.extremum.common.mapper.SystemJsonObjectMapper;
import com.extremum.common.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.extremum.authentication.models.dto.AuthenticationRequestDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

/**
 * @author rpuch
 */
public class Authenticator {
    private final WebTestClient webTestClient;

    public Authenticator(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public String obtainAnonAuthToken() throws JsonProcessingException {
        ObjectMapper objectMapper = new SystemJsonObjectMapper(new MockedMapperDependencies());

        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setApiKey("test");
        dto.setLocale("ru_RU");
        dto.setTimezone("test");
        Response response = webTestClient.post()
                .uri("/auth")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "all")
                .header(HttpHeaders.USER_AGENT, "test")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(dto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(Response.class)
                .getResponseBody()
                .blockFirst();
        String valueAsString = objectMapper.writeValueAsString(response);
        return JsonPath.parse(valueAsString).read("$.result.session.token");
    }
}

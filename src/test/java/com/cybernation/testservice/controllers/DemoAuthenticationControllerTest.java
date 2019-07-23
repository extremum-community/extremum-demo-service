package com.cybernation.testservice.controllers;

import com.cybernation.testservice.BaseApplicationTests;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.extremum.authentication.models.dto.AuthenticationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class DemoAuthenticationControllerTest extends BaseApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;

    private String authToken;

    @BeforeEach
    void setUp() throws JsonProcessingException {
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
        authToken = JsonPath.parse(valueAsString).read("$.result.session.token");
    }

    @Test
    void testRequireAuth() {
        webTestClient.get()
                .uri("/api/auth/req_auth")
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You are authenticated!");
    }

    @Test
    void testRequireAuthWithoutBearer() {
        webTestClient.get()
                .uri("/api/auth/req_auth")
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode().intValue());
                });
    }

    @Test
    void testBadRequireAuth() {
        webTestClient.get()
                .uri("/api/auth/req_auth")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode().intValue());
                });
    }

    @Test
    void testReqClient() {
        webTestClient.get()
                .uri("/api/auth/req_client")
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode().intValue());
                });

    }

    @Test
    void testReqAnonym() {
        webTestClient.get()
                .uri("/api/auth/req_anonym")
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You have anonym role!");
    }
}
package com.cybernation.testservice.controllers;

import com.cybernation.testservice.BaseApplicationTests;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.extremum.authentication.models.dto.AuthenticationRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("auth")
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class DemoAuthenticationControllerTest extends BaseApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;

    private String authToken;

    @BeforeAll
    @SuppressWarnings("unchecked")
    void setUp() {
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setApiKey("test");
        dto.setLocale("ru_RU");
        dto.setTimezone("test");
        webTestClient.post()
                .uri("/auth")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "all")
                .header(HttpHeaders.USER_AGENT, "test")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(dto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(Response.class)
                .getResponseBody()
                .subscribe(response -> {
                    try {
                        String responseAsString = objectMapper.writeValueAsString(response.getResult());
                        authToken = JsonPath.parse(responseAsString).read("$.session.token");
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testRequireAuth() {
        webTestClient.get()
                .uri("/api/req_auth")
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You are authenticated!");
    }

    @Test
    void testReqClient() {
        webTestClient.get()
                .uri("/api/req_client")
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(401, response.getCode().intValue());
                });
    }
}
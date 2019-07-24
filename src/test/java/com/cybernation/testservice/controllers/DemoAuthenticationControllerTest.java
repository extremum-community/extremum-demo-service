package com.cybernation.testservice.controllers;

import com.cybernation.testservice.Authenticator;
import com.cybernation.testservice.BaseApplicationTests;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.cybernation.testservice.Authorization.bearer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class DemoAuthenticationControllerTest extends BaseApplicationTests {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private WebTestClient webTestClient;

    private String authToken;

    @BeforeEach
    void obtainAnonAuthToken() throws JsonProcessingException {
        authToken = new Authenticator(webTestClient).obtainAnonAuthToken();
    }

    @Test
    void testRequireAuth() {
        webTestClient.get()
                .uri("/api/req_auth")
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You are authenticated!");
    }

    @Test
    void testBadRequireAuth() throws InterruptedException {
        webTestClient.get()
                .uri("/api/req_auth")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode().intValue());
                });

        Thread.sleep(5000);

        Object value = redissonClient.getMap("auth_test").get("req_auth");
        assertNull(value);
    }

    @Test
    void testReqClient() throws InterruptedException {
        webTestClient.get()
                .uri("/api/req_client")
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(response -> {
                    assertEquals(ResponseStatusEnum.FAIL, response.getStatus());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode().intValue());
                });

        Thread.sleep(5000);

        String value = (String) redissonClient.getMap("auth_test").get("req_client");
        assertNull(value);
    }

    @Test
    void testReqAnonym() {
        webTestClient.get()
                .uri("/api/req_anonym")
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You have anonym role!");
    }
}
package com.cybernation.testservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.extremum.authentication.spi.interfaces.TokenVerifier;
import io.extremum.sharedmodels.dto.Response;
import io.extremum.sharedmodels.dto.ResponseStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.cybernation.testservice.Authorization.bearer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(Lifecycle.PER_CLASS)
class DemoSecuredControllerTest extends BaseApplicationTests {
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
                .uri("/api/auth/req_auth")
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You are authenticated!");
    }

    @Test
    void testRequireAuthWithoutBearer() {
        String tokenWithoutBearer = authToken.substring(TokenVerifier.BEARER_PREFIX_LENGTH);

        webTestClient.get()
                .uri("/api/auth/req_auth")
                .header(HttpHeaders.AUTHORIZATION, tokenWithoutBearer)
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
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
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
                .header(HttpHeaders.AUTHORIZATION, bearer(authToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("You have anonym role!");
    }

    @Test
    void testOptionsNotSecured() {
        webTestClient.options()
                .uri("/api/auth/test_options")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo("OPTIONS not need to be secured!");
    }
}
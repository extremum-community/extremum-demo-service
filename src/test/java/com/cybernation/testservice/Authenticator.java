package com.cybernation.testservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.extremum.authentication.common.models.dto.AuthenticationRequestDto;
import io.extremum.common.mapper.SystemJsonObjectMapper;
import io.extremum.sharedmodels.dto.Response;
import io.extremum.sharedmodels.personal.Credential;
import io.extremum.sharedmodels.personal.VerifyType;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rpuch
 */
public class Authenticator {
    private final WebTestClient webTestClient;

    public Authenticator(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    public String obtainAnonAuthToken() throws JsonProcessingException {
        return obtainTokenWithCredentials(null);
    }

    public String obtainAuthTokenWithLoginAndPassword(String login, String password) throws JsonProcessingException {
        List<Credential> credentials = new ArrayList<>();
        credentials.add(new Credential("test", VerifyType.USERNAME, login));
        credentials.add(new Credential("test", VerifyType.PASSWORD, password));

        return obtainTokenWithCredentials(credentials);
    }

    private String obtainTokenWithCredentials(List<Credential> credentials) throws JsonProcessingException {
        ObjectMapper objectMapper = new SystemJsonObjectMapper(new MockedMapperDependencies());

        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setApiKey("test");
        dto.setLocale("ru_RU");
        dto.setTimezone("test");
        dto.setCredentials(credentials);
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

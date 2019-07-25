package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.cybernation.testservice.services.jpa.FlyService;
import com.extremum.common.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.Map;

import static com.cybernation.testservice.Authorization.bearer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class LightJpaModelDefaultServicesTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FlyService flyService;

    private Fly fly;

    private String anonToken;

    @BeforeEach
    void createAFreshFly() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();

        fly = new Fly();
        fly.setName("Mosca");

        fly = flyService.create(fly);
    }

    @Test
    void testEverythingGet() {
        Map<String, Object> responseBody = retrieveViaEverythingGetSuccessfully();
        assertThat(responseBody.get("name"), is("Mosca"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> retrieveViaEverythingGetSuccessfully() {
        return (Map<String, Object>) webTestClient.get()
                .uri("/" + flyExternalId())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(ResponseAssert.isSuccessful())
                .returnResult()
                .getResponseBody()
                .getResult();
    }

    private String flyExternalId() {
        return fly.getUuid().getExternalId();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testEverythingPatch() throws Exception {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/name"), new TextNode("Mosca II"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));

        Map<String, Object> responseBody = (Map<String, Object>) webTestClient.patch()
                .uri("/" + flyExternalId())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(ResponseAssert.isSuccessful())
                .returnResult()
                .getResponseBody()
                .getResult();
        assertThat(responseBody.get("name"), is("Mosca II"));

        responseBody = retrieveViaEverythingGetSuccessfully();
        assertThat(responseBody.get("name"), is("Mosca II"));
    }

    @Test
    void givenAModelIsAlreadyDeleted_whenDeletingIt_thenShouldReturn404() {
        webTestClient.delete()
                .uri("/" + flyExternalId())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(ResponseAssert.isSuccessful());

        Response response = webTestClient.get()
                .uri("/" + flyExternalId())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        assertThat(response, is(notNullValue()));
        assertThat(response.getCode(), is(404));
    }
}

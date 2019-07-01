package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.cybernation.testservice.services.jpa.FlyService;
import com.extremum.common.response.Response;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.Map;

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

    @BeforeEach
    void createAFreshFly() {
        fly = new Fly();
        fly.setName("Mosca");

        fly = flyService.create(fly);
    }

    @Test
    void testEverythingGet() {
        Map<String, Object> responseBody = retrieveViaEverythingGet();
        assertThat(responseBody.get("name"), is("Mosca"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> retrieveViaEverythingGet() {
        return (Map<String, Object>) webTestClient.get()
                    .uri("/" + flyExternalId())
                    .exchange()
                    .expectStatus().is2xxSuccessful()
                    .expectBody(Response.class)
                    .value(System.out::println)
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
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody()
                .getResult();
        assertThat(responseBody.get("name"), is("Mosca II"));

        responseBody = retrieveViaEverythingGet();
        assertThat(responseBody.get("name"), is("Mosca II"));
    }

    @Test
    void testEverythingDelete() {
        webTestClient.delete()
                .uri("/" + flyExternalId())
                .exchange()
                .expectStatus().is2xxSuccessful();

        Response response = webTestClient.get()
                .uri("/" + flyExternalId())
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

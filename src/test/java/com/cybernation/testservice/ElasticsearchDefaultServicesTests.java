package com.cybernation.testservice;

import com.cybernation.testservice.models.elasticsearch.RubberBand;
import com.cybernation.testservice.services.elasticsearch.RubberBandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import io.extremum.sharedmodels.dto.Response;
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
import static com.cybernation.testservice.ResponseAssert.isSuccessful;
import static com.cybernation.testservice.UrlPrefix.prefix;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class ElasticsearchDefaultServicesTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RubberBandService rubberBandService;

    private RubberBand rubberBand;

    private String anonToken;

    @BeforeEach
    void createAFreshRubberBand() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();

        rubberBand = new RubberBand();
        rubberBand.setName("My band");
        rubberBand.setColor("Red");

        rubberBand = rubberBandService.create(rubberBand);
    }

    @Test
    void testEverythingGet() {
        Map<String, Object> responseBody = retrieveViaEverythingGet();
        assertThat(responseBody.get("name"), is("My band"));
        assertThat(responseBody.get("color"), is("Red"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> retrieveViaEverythingGet() {
        return (Map<String, Object>) webTestClient.get()
                .uri(prefix("/" + rubberBandExternalId()))
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody()
                .getResult();
    }

    private String rubberBandExternalId() {
        return rubberBand.getUuid().getExternalId();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testEverythingPatch() throws Exception {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/name"),
                new TextNode("Someone else's band"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));

        Map<String, Object> responseBody = (Map<String, Object>) webTestClient.patch()
                .uri(prefix("/" + rubberBandExternalId()))
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody()
                .getResult();
        assertThat(responseBody.get("name"), is("Someone else's band"));

        responseBody = retrieveViaEverythingGet();
        assertThat(responseBody.get("name"), is("Someone else's band"));
    }

    @Test
    void testEverythingDelete() {
        webTestClient.delete()
                .uri(prefix("/" + rubberBandExternalId()))
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful());

        Response response = webTestClient.get()
                .uri(prefix("/" + rubberBandExternalId()))
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

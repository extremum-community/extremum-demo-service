package com.cybernation.testservice;

import com.cybernation.testservice.models.watch.Spectacle;
import com.cybernation.testservice.services.watch.SpectacleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import io.extremum.sharedmodels.dto.Response;
import io.extremum.test.poll.Poller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.cybernation.testservice.Authorization.bearer;
import static com.cybernation.testservice.ResponseAssert.isSuccessful;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class WatchTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SpectacleService spectacleService;

    private Spectacle spectacle;

    private String anonToken;

    @BeforeEach
    void init() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();

        spectacle = new Spectacle();
        spectacle.setName("Unforgiven");

        spectacle = spectacleService.create(spectacle);
    }

    @Test
    void whenSubscribedAndModelPatched_thenOneWatchEventShouldBeAvailableViaGET() throws Exception {
        subscribeToSpectacle();
        patchViaEverythingEverything();

        List<Map<String, Object>> events = getNonEmptyEvents();

        assertThatThereIsOneEventForPatchingNameProperty(events);
    }

    private void subscribeToSpectacle() {
        webTestClient.put()
                .uri("/watch")
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .body(BodyInserters.fromObject(singletonList(spectacleExternalId())))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult();
    }

    private String spectacleExternalId() {
        return spectacle.getUuid().getExternalId();
    }

    private void patchViaEverythingEverything() throws JsonPointerException {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/name"),
                new TextNode("Unforgiven II"));
        JsonPatch jsonPatch = new JsonPatch(singletonList(operation));

        webTestClient.patch()
                .uri("/" + spectacleExternalId())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody();
    }

    private List<Map<String, Object>> getNonEmptyEvents() throws InterruptedException {
        Poller poller = new Poller(Duration.ofSeconds(10));
        return poller.poll(this::getEvents, events -> events.size() > 0);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getEvents() {
        return (List<Map<String, Object>>) webTestClient.get()
                            .uri("/watch")
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

    private void assertThatThereIsOneEventForPatchingNameProperty(List<Map<String, Object>> events) {
        assertThat(events, hasSize(1));
        Map<String, Object> event = events.get(0);

        assertThatObjectSectionIsCorrect(event);

        Map<String, Object> operation = getSingleOperation(event);

        assertThat(operation, hasEntry(is("op"), is("replace")));
        assertThat(operation, hasEntry(is("path"), is("/name")));
        assertThat(operation, hasEntry(is("value"), is("Unforgiven II")));
    }

    private void assertThatObjectSectionIsCorrect(Map<String, Object> event) {
        assertThat(event.get("object"), is(notNullValue()));
        assertThat(event.get("object"), is(instanceOf(Map.class)));
        @SuppressWarnings("unchecked")
        Map<String, Object> object = (Map<String, Object>) event.get("object");
        assertThat(object, hasEntry(is("id"), equalTo(spectacleExternalId())));
        assertThat(object, hasEntry(is("model"), is("Spectacle")));
        assertThat(object, hasKey("created"));
        assertThat(object, hasKey("modified"));
        assertThat(object, hasKey("version"));
    }

    private Map<String, Object> getSingleOperation(Map<String, Object> event) {
        assertThat(event.get("patch"), is(notNullValue()));
        assertThat(event.get("patch"), is(instanceOf(List.class)));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> operations = (List<Map<String, Object>>) event.get("patch");
        assertThat(operations, hasSize(1));
        return operations.get(0);
    }
}

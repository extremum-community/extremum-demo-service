package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.basic.Fly;
import com.cybernation.testservice.models.jpa.basic.Swarm;
import com.cybernation.testservice.services.jpa.SwarmService;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static com.cybernation.testservice.Authorization.bearer;
import static com.cybernation.testservice.ResponseAssert.isSuccessful;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class JpaBasicModelCollectionTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private SwarmService swarmService;

    private Swarm swarm;

    private String anonToken;

    @BeforeEach
    void obtainAnonToken() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();
    }

    @BeforeAll
    void createSwarm() {
        swarm = new Swarm();
        swarm.setName("Flies incorporated");
        swarm.addFly(new Fly("Mouche"));
        swarm.addFly(new Fly("Mosca"));

        swarm = swarmService.create(swarm);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Test
    void fetchAnAutoCollection() {

        Map<String, Object> swarmMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + swarm.getUuid())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> fliesMap = (Map<String, Object>) swarmMap.get("flies");
        String fliesCollectionUrl = (String) fliesMap.get("url");
        assertNotNull(fliesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        List<Map<String, Object>> flies = (List<Map<String, Object>>) webTestClient.get()
                .uri(fliesCollectionUrl)
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(matcher -> assertThat(matcher.getStatus(), is(ResponseStatusEnum.OK)))
                .returnResult()
                .getResponseBody().getResult();

        assertThat(flies, hasSize(2));
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Test
    void fetchACollectionWithCustomFetcher() {
        Map<String, Object> swarmMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + swarm.getUuid())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> fliesMap = (Map<String, Object>) swarmMap.get("customFlies");
        String fliesCollectionUrl = (String) fliesMap.get("url");
        assertNotNull(fliesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        List<Map<String, Object>> flies = (List<Map<String, Object>>) webTestClient.get()
                .uri(fliesCollectionUrl)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(matcher -> assertThat(matcher.getStatus(), is(ResponseStatusEnum.OK)))
                .returnResult()
                .getResponseBody().getResult();

        assertThat(flies, hasSize(2));
    }
}

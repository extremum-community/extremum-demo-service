package com.cybernation.testservice;

import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.cybernation.testservice.models.House;
import com.cybernation.testservice.models.Street;
import com.cybernation.testservice.services.HouseService;
import com.cybernation.testservice.services.StreetService;
import com.extremum.common.response.Response;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.GenericContainer;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DescriptorsTestServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    private static GenericContainer redis = new GenericContainer("redis:5.0.4").withExposedPorts(6379);
    private static GenericContainer mongo = new GenericContainer("mongo:3.4-xenial").withExposedPorts(27017);

    private String descriptorId;
    private String everythingDescriptorId;
    private String removeDescriptorId;

    private House house1;
    private House house2;

    @Autowired
    private HouseService houseService;
    @Autowired
    private StreetService streetService;

    static {
        Stream.of(redis, mongo).forEach(GenericContainer::start);
        System.setProperty("redis.uri", "redis://" + redis.getContainerIpAddress() + ":" + redis.getFirstMappedPort());
        System.setProperty("mongo.uri", "mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getFirstMappedPort());
    }

    @BeforeAll
    void loadData() {
        DemoMongoModelRequestDto dto = new DemoMongoModelRequestDto();
        dto.setTestId("3333");
        DemoMongoModelResponseDto responseBody = webTestClient
                .post()
                .uri("/api")
                .body(BodyInserters.fromObject(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        descriptorId = responseBody.getId().getExternalId();

        DemoMongoModelRequestDto everythingDto = new DemoMongoModelRequestDto();
        everythingDto.setTestId("1000");
        DemoMongoModelResponseDto everythingBody = webTestClient
                .post()
                .uri("/api")
                .body(BodyInserters.fromObject(everythingDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        everythingDescriptorId = everythingBody.getId().getExternalId();

        DemoMongoModelRequestDto removalDto = new DemoMongoModelRequestDto();
        DemoMongoModelResponseDto removalResponse = webTestClient
                .post()
                .uri("/api")
                .body(BodyInserters.fromObject(removalDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        removeDescriptorId = removalResponse.getId().getExternalId();
    }

    @Test
    void getById() {
        webTestClient.get()
                .uri("/api/12112")
                .exchange()
                .expectStatus().is5xxServerError();

        DemoMongoModelResponseDto responseBody = webTestClient.get()
                .uri("/api/" + descriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertEquals(responseBody.getVersion().longValue(), 1L);
        assertEquals(responseBody.getTestId(), "1212");
    }

    @Test
    void updateById() {
        webTestClient
                .put()
                .uri("/api/12112")
                .body(BodyInserters.fromObject(new DemoMongoModelRequestDto()))
                .exchange()
                .expectStatus().is5xxServerError();

        DemoMongoModelResponseDto responseBody = webTestClient.put()
                .uri("/api/" + descriptorId)
                .body(BodyInserters.fromObject(new DemoMongoModelRequestDto("1212")))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertEquals(responseBody.getTestId(), "1212");
    }

    @Test
    @SuppressWarnings("unchecked")
    void getByDescriptorId() {
        Map<String, Object> responseBody = (Map<String, Object>) webTestClient
                .get()
                .uri("/" + everythingDescriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertNotNull(responseBody);
        assertEquals(responseBody.get("testId"), "1000");
    }

    @Test
    @SuppressWarnings("unchecked")
    void patchByDescriptorId() throws JsonPointerException {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/testId"), new TextNode("1212"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));

        Map<String, Object> result = (Map<String, Object>) webTestClient
                .patch()
                .uri("/" + everythingDescriptorId)
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertNotNull(result);
        assertEquals(result.get("testId"), "1212");
    }

    @Test
    void removeByDescriptorId() {
        webTestClient.delete()
                .uri("/" + removeDescriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @SuppressWarnings("unchecked")
    @Test
    void fetchACollectionById() throws Exception {
        List<Object> housesCollectionList = create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollection(
                Collections.emptyMap());

        assertThat(housesCollectionList, hasSize(2));
        Map<String, Object> houseMap = (Map<String, Object>) housesCollectionList.get(0);
        assertThat(houseMap.get("id"), is(equalTo(house1.getUuid().getExternalId())));
        assertThat(houseMap.get("number"), is("1"));
    }

    @SuppressWarnings("unchecked")
    private List<Object> create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollection(
            Map<String, String> queryParams) throws Exception {
        house1 = houseService.create(new House("1"));
        house2 = houseService.create(new House("2a"));

        Street street = streetService.create(new Street("Test avenue",
                Arrays.asList(house1.getId().toString(), house2.getId().toString())));

        Map<String, Object> streetMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + street.getUuid())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> housesMap = (Map<String, Object>) streetMap.get("houses");
        String housesCollectionUrl = (String) housesMap.get("url");
        assertNotNull(housesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        URI uri = buildUriWithEncodedQueryString(queryParams, housesCollectionUrl);
        return (List<Object>) webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();
    }

    @NotNull
    private URI buildUriWithEncodedQueryString(Map<String, String> queryParams,
                                               String housesCollectionUrl) throws URISyntaxException {
        String encodedQueryParams = queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + urlEncode(entry.getValue()))
                .collect(Collectors.joining("&"));
        return new URI(housesCollectionUrl + "?" + encodedQueryParams);
    }

    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void fetchACollectionByIdWithProjection() throws Exception {
        List<Object> housesCollectionList = create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollection(
                ImmutableMap.of(
                        "offset", "1",
                        "limit", "10",
                        "since", "2000-04-30T15:20:29.578+0000",
                        "until", "2100-04-30T15:20:29.578+0000"
                ));

        assertThat(housesCollectionList, hasSize(1));
        Map<String, Object> houseMap = (Map<String, Object>) housesCollectionList.get(0);
        assertThat(houseMap.get("id"), is(equalTo(house2.getUuid().getExternalId())));
        assertThat(houseMap.get("number"), is("2a"));
    }
}

package com.cybernation.testservice;

import com.cybernation.testservice.models.DemoMongoModelRequestDto;
import com.cybernation.testservice.models.DemoMongoModelResponseDto;
import com.extremum.common.response.Response;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DescriptorsTestServiceApplicationTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    private String descriptorId;
    private String everythingDescriptorId;
    private String removeDescriptorId;

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
}

package com.cybernation.testservice;

import com.cybernation.testservice.dto.DemoMongoModelRequestDto;
import com.cybernation.testservice.dto.DemoMongoModelResponseDto;
import com.cybernation.testservice.dto.DepartmentRequestDto;
import com.cybernation.testservice.dto.DepartmentResponseDto;
import com.cybernation.testservice.models.mongo.DemoMongoModel;
import com.cybernation.testservice.services.mongo.DemoMongoService;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DescriptorsTestServiceApplicationTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DemoMongoService demoMongoService;

    private String descriptorId;
    private String everythingMongoDescriptorId;
    private String jpaDescriptorId;

    @BeforeEach
    void loadData() {
        DemoMongoModelRequestDto dto = new DemoMongoModelRequestDto();
        dto.setTestId("3333");
        DemoMongoModelResponseDto responseBody = webTestClient
                .post()
                .uri("/api/mongo")
                .body(BodyInserters.fromObject(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        descriptorId = responseBody.getId().getExternalId();

        DemoMongoModelRequestDto everythingMongoDto = new DemoMongoModelRequestDto();
        everythingMongoDto.setTestId("1000");
        DemoMongoModelResponseDto everythingBody = webTestClient
                .post()
                .uri("/api/mongo")
                .body(BodyInserters.fromObject(everythingMongoDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        everythingMongoDescriptorId = everythingBody.getId().getExternalId();

        DepartmentRequestDto everythingJpaDto = new DepartmentRequestDto();
        everythingJpaDto.setName("test_depart");
        DepartmentResponseDto departmentResponseDto = webTestClient
                .post()
                .uri("/api/jpa")
                .body(BodyInserters.fromObject(everythingJpaDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DepartmentResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        jpaDescriptorId = departmentResponseDto.getId().getExternalId();
    }

    @Test
    void getById() {
        webTestClient.get()
                .uri("/api/mongo/12112")
                .exchange()
                .expectStatus().is5xxServerError();

        DemoMongoModelResponseDto responseBody = webTestClient.get()
                .uri("/api/mongo/" + descriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(DemoMongoModelResponseDto.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertAll(() -> {
            assertNotNull(responseBody);
            assertEquals(responseBody.getVersion().longValue(), 0L);
            assertEquals(responseBody.getTestId(), "3333");
        });
    }

    @Test
    void updateById() {
        webTestClient
                .put()
                .uri("/api/mongo/12112")
                .body(BodyInserters.fromObject(new DemoMongoModelRequestDto()))
                .exchange()
                .expectStatus().is5xxServerError();

        DemoMongoModelResponseDto responseBody = webTestClient.put()
                .uri("/api/mongo/" + descriptorId)
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
    void getMongoByDescriptorId() {
        Map<String, Object> responseBody = (Map<String, Object>) webTestClient
                .get()
                .uri("/" + everythingMongoDescriptorId)
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
    void patchMongoByDescriptorId() throws JsonPointerException {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/testId"), new TextNode("1212"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));

        Map<String, Object> result = (Map<String, Object>) webTestClient
                .patch()
                .uri("/" + everythingMongoDescriptorId)
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
    void removeMongoByDescriptorId() {
        webTestClient.delete()
                .uri("/" + everythingMongoDescriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @SuppressWarnings("unchecked")
    void getJpaByDescriptorId() {
        Map<String, Object> responseBody = (Map<String, Object>) webTestClient
                .get()
                .uri("/" + jpaDescriptorId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertNotNull(responseBody);
        assertEquals(responseBody.get("name"), "test_depart");
    }

    @Test
    @SuppressWarnings("unchecked")
    void patchJpaByDescriptorId() throws JsonPointerException {
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/name"), new TextNode("prod_depart"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));

        Map<String, Object> result = (Map<String, Object>) webTestClient
                .patch()
                .uri("/" + jpaDescriptorId)
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertNotNull(result);
        assertEquals(result.get("name"), "prod_depart");
    }

    @Test
    void whenGettingANonExistingRecordWithExistingDescriptorViaEverythingEverything_thenHttpCode200AndCode404InResponseMessageShouldBeReturned() {
        DemoMongoModel model = demoMongoService.create(new DemoMongoModel());
        demoMongoService.delete(model.getId().toString());

        Response response = webTestClient.get()
                .uri("/" + model.getUuid().getExternalId())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(ResponseStatusEnum.FAIL));
        assertThat(response.getCode(), is(404));
    }

    @Test
    void whenGettingANonExistentRecordViaEverythingEverything_thenHttpCode200AndCode404InResponseMessageShouldBeReturned() {
        Response response = webTestClient.get()
                .uri("/does-not-exist")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(ResponseStatusEnum.FAIL));
        assertThat(response.getCode(), is(404));
    }

    @Test
    void whenPatchingANonExistentRecordViaEverythingEverything_thenHttpCode200AndCode404InResponseMessageShouldBeReturned()
            throws Exception{
        JsonPatchOperation operation = new ReplaceOperation(new JsonPointer("/testId"), new TextNode("1212"));
        JsonPatch jsonPatch = new JsonPatch(Collections.singletonList(operation));
        
        Response response = webTestClient.patch()
                .uri("/does-not-exist")
                .body(BodyInserters.fromObject(jsonPatch))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(ResponseStatusEnum.FAIL));
        assertThat(response.getCode(), is(404));
    }

    @Test
    void whenDeletigANonExistentRecordViaEverythingEverything_thenHttpCode200AndCode404InResponseMessageShouldBeReturned() {
        Response response = webTestClient.delete()
                .uri("/does-not-exist")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is(ResponseStatusEnum.FAIL));
        assertThat(response.getCode(), is(404));
    }
}

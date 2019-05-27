package com.cybernation.testservice;

import com.cybernation.testservice.models.House;
import com.cybernation.testservice.models.Street;
import com.cybernation.testservice.services.HouseService;
import com.cybernation.testservice.services.StreetService;
import com.extremum.common.response.Response;
import com.extremum.common.response.ResponseStatusEnum;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoCollectionTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    private House house1;
    private House house2;

    @Autowired
    private HouseService houseService;
    @Autowired
    private StreetService streetService;

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

    @Test
    void fetchANonExistentCollection() {
        Response response = webTestClient.get()
                .uri("/collection/no-such-collection")
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

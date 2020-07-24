package com.cybernation.testservice;

import com.cybernation.testservice.models.mongo.House;
import com.cybernation.testservice.models.mongo.Street;
import com.cybernation.testservice.services.mongo.HouseService;
import com.cybernation.testservice.services.mongo.StreetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import io.extremum.sharedmodels.dto.Response;
import io.extremum.sharedmodels.dto.ResponseStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.cybernation.testservice.Authorization.bearer;
import static com.cybernation.testservice.ResponseAssert.isSuccessful;
import static com.cybernation.testservice.UrlPrefix.prefix;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class MongoCollectionTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    private House house1;
    private House house2;

    @Autowired
    private HouseService houseService;
    @Autowired
    private StreetService streetService;

    private String anonToken;

    @BeforeEach
    void obtainAnonToken() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();
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
        Response response = create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollectionResponse(queryParams);
        return (List<Object>) response.getResult();
    }

    @SuppressWarnings("unchecked")
    private Response create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollectionResponse(
            Map<String, String> queryParams) throws URISyntaxException {
        house1 = houseService.create(new House("1"));
        house2 = houseService.create(new House("2a"));

        Street street = streetService.create(new Street("Test avenue",
                Arrays.asList(house1.getId().toString(), house2.getId().toString())));

        Map<String, Object> streetMap = (Map<String, Object>) webTestClient.get()
                .uri(prefix("/" + street.getUuid()))
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> housesMap = (Map<String, Object>) streetMap.get("houses");
        String housesCollectionUrl = (String) housesMap.get("url");
        assertNotNull(housesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        URI uri = TestUris.buildUriWithEncodedQueryString(queryParams, housesCollectionUrl);
        return webTestClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
    }

    @SuppressWarnings("unchecked")
    @Test
    void fetchACollectionByIdWithProjection() throws Exception {
        List<Object> housesCollectionList = create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollection(
                ImmutableMap.of(
                        "offset", "1",
                        "limit", "10",
                        "since", "2000-04-30T15:20:29.578000+00:00",
                        "until", "2100-04-30T15:20:29.578900+00:00"
                ));

        assertThat(housesCollectionList, hasSize(1));
        Map<String, Object> houseMap = (Map<String, Object>) housesCollectionList.get(0);
        assertThat(houseMap.get("id"), is(equalTo(house2.getUuid().getExternalId())));
        assertThat(houseMap.get("number"), is("2a"));
    }

    @Test
    void fetchANonExistentCollection() {
        Response response = webTestClient.get()
                .uri(prefix("/" + UUID.randomUUID()))
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
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
    void testThatPaginationIsReturned() throws Exception {
        Response response = create2HousesAnd1StreetAndObtainHousesFromStreetHousesCollectionResponse(
                Collections.emptyMap());

        assertThat(response.getPagination(), is(notNullValue()));
        assertThat(response.getPagination().getCount(), is(2));
        assertThat(response.getPagination().getOffset(), is(0));
        assertThat(response.getPagination().getTotal(), is(2L));
        assertThat(response.getPagination().getSince(), is(nullValue()));
        assertThat(response.getPagination().getUntil(), is(nullValue()));
    }
}

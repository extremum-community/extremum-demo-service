package com.cybernation.testservice;

import com.cybernation.testservice.dto.HouseResponseDto;
import com.cybernation.testservice.models.mongo.House;
import com.cybernation.testservice.models.mongo.Street;
import com.cybernation.testservice.services.mongo.HouseService;
import com.cybernation.testservice.services.mongo.StreetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import io.extremum.sharedmodels.dto.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.*;

import static com.cybernation.testservice.Authorization.bearer;
import static com.cybernation.testservice.ResponseAssert.isSuccessful;
import static com.cybernation.testservice.UrlPrefix.prefix;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class MongoCollectionStreamingTests extends BaseApplicationTests {
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

    @Test
    void streamAMongoCollectionById() throws Exception {
        List<HouseResponseDto> housesCollectionList = create2HousesAnd1StreetAndStreamHousesFromStreetHousesCollection(
                Collections.emptyMap());

        assertThat(housesCollectionList, hasSize(2));
        HouseResponseDto firstHouse = housesCollectionList.get(0);
        assertThat(firstHouse.getId().getExternalId(), is(equalTo(house1.getUuid().getExternalId())));
        assertThat(firstHouse.getNumber(), is("1"));
    }

    private List<HouseResponseDto> create2HousesAnd1StreetAndStreamHousesFromStreetHousesCollection(
            Map<String, String> queryParams) throws Exception {
        house1 = houseService.create(new House("1"));
        house2 = houseService.create(new House("2a"));

        Street street = streetService.create(new Street("Test avenue",
                Arrays.asList(house1.getId().toString(), house2.getId().toString())));

        Map<String, Object> streetMap = getStreet(street);

        @SuppressWarnings("unchecked")
        Map<String, Object> housesMap = (Map<String, Object>) streetMap.get("houses");
        String housesCollectionUrl = (String) housesMap.get("url");
        assertNotNull(housesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        URI uri = TestUris.buildUriWithEncodedQueryString(queryParams, housesCollectionUrl);
        return webTestClient.get()
                .uri(uri)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(HouseResponseDto.class)
                .returnResult()
                .getResponseBody();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getStreet(Street street) {
        return (Map<String, Object>) webTestClient.get()
                .uri(prefix("/" + street.getUuid()))
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();
    }

    @Test
    void streamAMontoCollectionByIdWithProjection() throws Exception {
        List<HouseResponseDto> housesCollectionList = create2HousesAnd1StreetAndStreamHousesFromStreetHousesCollection(
                ImmutableMap.of(
                        "offset", "1",
                        "limit", "10",
                        "since", "2000-04-30T15:20:29.578000+0000",
                        "until", "2100-04-30T15:20:29.578900+0000"
                ));

        assertThat(housesCollectionList, hasSize(1));
        HouseResponseDto house = housesCollectionList.get(0);
        assertThat(house.getId().getExternalId(), is(equalTo(house2.getUuid().getExternalId())));
        assertThat(house.getNumber(), is("2a"));
    }

    @Test
    void streamANonExistentCollection() {
        List<HouseResponseDto> houses = webTestClient.get()
                .uri(prefix("/" + UUID.randomUUID()))
                .accept(MediaType.TEXT_EVENT_STREAM)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(HouseResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(houses, hasSize(0));
    }
}

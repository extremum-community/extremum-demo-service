package com.cybernation.testservice;

import com.cybernation.testservice.dao.auth.UserDao;
import com.cybernation.testservice.models.auth.TopSecret;
import com.cybernation.testservice.models.auth.User;
import com.cybernation.testservice.services.auth.TopSecretService;
import com.extremum.common.response.Response;
import com.extremum.sharedmodels.descriptor.Descriptor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static com.cybernation.testservice.Authorization.bearer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class AuthenticatedEverythingTests extends BaseApplicationTests {
    private static final String SECRET = "This is a secret!";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserDao userDao;
    @Autowired
    private TopSecretService topSecretService;

    private String privilegedLogin;
    private String anonToken;
    private String privilegedToken;

    private Descriptor topSecretId;

    @BeforeAll
    void init() throws Exception {
        privilegedLogin = randomName();

        User privilegedUser = new User();
        privilegedUser.setUsername(privilegedLogin);
        privilegedUser.setPassword("pass");
        privilegedUser.setRoles(Collections.singleton("ROLE_TOP_SECRET"));
        userDao.save(privilegedUser);

        TopSecret topSecret = new TopSecret();
        topSecret.setSecret(SECRET);
        topSecretService.save(topSecret);
        topSecretId = topSecret.getUuid();

        Authenticator authenticator = new Authenticator(webTestClient);
        anonToken = authenticator.obtainAnonAuthToken();

        privilegedToken = authenticator.obtainAuthTokenWithLoginAndPassword(privilegedLogin, "pass");
    }

    @NotNull
    private String randomName() {
        return UUID.randomUUID().toString();
    }

    @Test
    void whenAccessingByAnonUser_then403ShouldBeReturned() {
        webTestClient.get()
                .uri("/" + topSecretId)
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(ResponseAssert.is403());
    }

    @SuppressWarnings("unchecked")
    @Test
    void whenAccessingByPrivilegedUser_thenResultShouldBeReturned() {
        Map<String, Object> responseBody = (Map<String, Object>) webTestClient.get()
                .uri("/" + topSecretId)
                .header(HttpHeaders.AUTHORIZATION, bearer(privilegedToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(ResponseAssert.isSuccessful())
                .returnResult()
                .getResponseBody()
                .getResult();
        assertThat(responseBody.get("secret"), is(SECRET));
    }

}

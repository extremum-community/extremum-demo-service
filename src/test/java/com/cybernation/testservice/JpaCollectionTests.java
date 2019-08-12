package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.cybernation.testservice.services.jpa.DepartmentService;
import io.extremum.common.response.Response;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
class JpaCollectionTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DepartmentService departmentService;

    private Department department;

    private String anonToken;

    @BeforeEach
    void obtainAnonToken() throws JsonProcessingException {
        anonToken = new Authenticator(webTestClient).obtainAnonAuthToken();
    }

    @BeforeAll
    void createDepartment() {
        department = new Department();
        department.setName("Finance");
        department.addEmployee(new Employee("Tim"));
        department.addEmployee(new Employee("Ann"));

        department = departmentService.create(department);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Test
    void fetchAnAutoCollection() {

        Map<String, Object> departmentMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + department.getUuid())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> employeesMap = (Map<String, Object>) departmentMap.get("employees");
        String employeesCollectionUrl = (String) employeesMap.get("url");
        assertNotNull(employeesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        List<Map<String, Object>> employees = (List<Map<String, Object>>) webTestClient.get()
                .uri(employeesCollectionUrl)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertThat(employees, hasSize(2));
        assertThat(employees.get(0).get("department"), is(equalTo(department.getUuid().getExternalId())));
        assertThat(employees.get(1).get("department"), is(equalTo(department.getUuid().getExternalId())));
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Test
    void fetchACollectionWithCustomFetcher() {
        Map<String, Object> departmentMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + department.getUuid())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> employeesMap = (Map<String, Object>) departmentMap.get("customEmployees");
        String employeesCollectionUrl = (String) employeesMap.get("url");
        assertNotNull(employeesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        List<Map<String, Object>> employees = (List<Map<String, Object>>) webTestClient.get()
                .uri(employeesCollectionUrl)
                .header(HttpHeaders.AUTHORIZATION,bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        assertThat(employees, hasSize(2));
        assertThat(employees.get(0).get("department"), is(equalTo(department.getUuid().getExternalId())));
        assertThat(employees.get(1).get("department"), is(equalTo(department.getUuid().getExternalId())));
    }
}

package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.cybernation.testservice.services.jpa.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.extremum.sharedmodels.dto.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
class JpaCollectionStreamingTests extends BaseApplicationTests {
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

    @Test
    void streamAnAutoCollection() throws Exception {
        streamCollectionOfEmployeesAndAssertItContainsTimAndAnn("employees");
    }

    @Test
    void streamACollectionWithCustomFetcher() throws Exception {
        streamCollectionOfEmployeesAndAssertItContainsTimAndAnn("customEmployees");
    }

    private void streamCollectionOfEmployeesAndAssertItContainsTimAndAnn(String employeeCollectionAttributeName)
            throws JSONException {
        Map<String, Object> departmentMap = getDepartment();
        String employeesCollectionUrl = getEmployeesCollectionUrl(employeeCollectionAttributeName, departmentMap);
        List<String> employeesJsons = getEmployeesJsons(employeesCollectionUrl);

        assertThatTimAndAnnAreReturned(employeesJsons);
    }

    private Map<String, Object> getDepartment() {
        Response response = webTestClient.get()
                .uri("/" + department.getUuid())
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .value(isSuccessful())
                .returnResult()
                .getResponseBody();
        assertThat(response, is(notNullValue()));

        @SuppressWarnings("unchecked")
        Map<String, Object> castResult = (Map<String, Object>) response.getResult();
        return castResult;
    }

    @NotNull
    private String getEmployeesCollectionUrl(String employeeCollectionAttributeName,
                                             Map<String, Object> departmentMap) {
        @SuppressWarnings("unchecked")
        Map<String, Object> employeesMap = (Map<String, Object>) departmentMap.get(employeeCollectionAttributeName);
        String employeesCollectionUrl = (String) employeesMap.get("url");
        assertNotNull(employeesCollectionUrl);
        return employeesCollectionUrl;
    }

    private List<String> getEmployeesJsons(String employeesCollectionUrl) {
        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        return webTestClient.get()
                .uri(employeesCollectionUrl)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(String.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
    }

    private void assertThatTimAndAnnAreReturned(List<String> employeesJsons) throws JSONException {
        assertThat(employeesJsons, hasSize(2));
        assertThatEmployeeIsAsExpected("Tim", employeesJsons.get(0));
        assertThatEmployeeIsAsExpected("Ann", employeesJsons.get(1));
    }

    private void assertThatEmployeeIsAsExpected(String expectedName, String employeeJson) throws JSONException {
        JSONObject firstEmployee = new JSONObject(employeeJson);
        assertThat(firstEmployee.getString("name"), is(expectedName));
        assertThat(firstEmployee.getString("department"), is(equalTo(department.getUuid().getExternalId())));
    }
}

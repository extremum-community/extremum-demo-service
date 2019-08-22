package com.cybernation.testservice;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.cybernation.testservice.services.jpa.DepartmentService;
import io.extremum.common.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
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

    @Test
    void fetchAnAutoCollection() {
        fetchCollectionOfEmployeesAndAssertItContainsTimAndAnn("employees");
    }

    @Test
    void fetchACollectionWithCustomFetcher() {
        fetchCollectionOfEmployeesAndAssertItContainsTimAndAnn("customEmployees");
    }

    private void fetchCollectionOfEmployeesAndAssertItContainsTimAndAnn(String employeeCollectionAttributeName) {
        Map<String, Object> departmentMap = getDepartment();
        String employeesCollectionUrl = getEmployeesCollectionUrl(employeeCollectionAttributeName, departmentMap);
        List<Map<String, Object>> employees = fetchEmployees(employeesCollectionUrl);

        assertThatTimAndAnnAreReturned(employees);
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

    private List<Map<String, Object>> fetchEmployees(String employeesCollectionUrl) {
        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        Response response = webTestClient.get()
                .uri(employeesCollectionUrl)
                .header(HttpHeaders.AUTHORIZATION, bearer(anonToken))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody();
        assertThat(response, is(notNullValue()));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> caseResult = (List<Map<String, Object>>) response.getResult();
        return caseResult;
    }

    private void assertThatTimAndAnnAreReturned(List<Map<String, Object>> employees) {
        assertThat(employees, hasSize(2));
        assertThatEmployeeIsAsExpected(employees.get(0), "Tim");
        assertThatEmployeeIsAsExpected(employees.get(1), "Ann");
    }

    private void assertThatEmployeeIsAsExpected(Map<String, Object> employee, String expectedName) {
        assertThat(employee.get("name"), is(expectedName));
        assertThat(employee.get("department"), is(equalTo(department.getUuid().getExternalId())));
    }
}

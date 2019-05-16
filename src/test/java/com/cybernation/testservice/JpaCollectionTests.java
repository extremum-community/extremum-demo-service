package com.cybernation.testservice;

import com.cybernation.testservice.models.Department;
import com.cybernation.testservice.models.Employee;
import com.cybernation.testservice.services.DepartmentService;
import com.extremum.common.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaCollectionTests extends BaseApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DepartmentService departmentService;

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Test
    void fetchACollection() {

        Department department = new Department();
        department.setName("Finance");
        department.addEmployee(new Employee("Tim"));
        department.addEmployee(new Employee("Ann"));

        department = departmentService.create(department);

        Map<String, Object> departmentMap = (Map<String, Object>) webTestClient.get()
                .uri("/" + department.getUuid())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(System.out::println)
                .returnResult()
                .getResponseBody().getResult();

        Map<String, Object> employeesMap = (Map<String, Object>) departmentMap.get("employees");
        String employeesCollectionUrl = (String) employeesMap.get("url");
        assertNotNull(employeesCollectionUrl);

        // encoding query parameters manually because WebTestClient does not encode + sign
        // by default, and the default servlet container does decode it
        List<Map<String, Object>> employees = (List<Map<String, Object>>) webTestClient.get()
                .uri(employeesCollectionUrl)
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

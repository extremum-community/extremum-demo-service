package com.cybernation.testservice.services.collectionfetcher;

import com.cybernation.testservice.models.Department;
import com.cybernation.testservice.models.Employee;
import com.cybernation.testservice.services.EmployeeService;
import com.extremum.everything.collection.Projection;
import com.extremum.everything.services.CollectionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rpuch
 */
@Service
@RequiredArgsConstructor
public class DepartmentCustomEmployeesFetcher implements CollectionFetcher<Department, Employee> {
    private final EmployeeService employeeService;

    @Override
    public String getSupportedModel() {
        return Department.MODEL_NAME;
    }

    @Override
    public String getHostFieldName() {
        return "customEmployees";
    }

    @Override
    public List<Employee> fetchCollection(Department department, Projection projection) {
        return employeeService.findByDepartmentId(department.getId());
    }
}

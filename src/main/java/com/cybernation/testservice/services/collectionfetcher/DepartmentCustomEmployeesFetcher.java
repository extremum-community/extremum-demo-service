package com.cybernation.testservice.services.collectionfetcher;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.cybernation.testservice.services.jpa.EmployeeService;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.everything.collection.Projection;
import io.extremum.everything.services.OwnedCollectionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
@RequiredArgsConstructor
public class DepartmentCustomEmployeesFetcher implements OwnedCollectionFetcher<Department, Employee> {
    private final EmployeeService employeeService;

    @Override
    public String getSupportedModel() {
        return Department.MODEL_NAME;
    }

    @Override
    public String getHostAttributeName() {
        return Department.CUSTOM_EMPLOYEES;
    }

    @Override
    public CollectionFragment<Employee> fetchCollection(Department department, Projection projection) {
        return employeeService.findByDepartmentId(department.getId());
    }
}

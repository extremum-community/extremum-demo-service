package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.cybernation.testservice.services.jpa.EmployeeService;
import io.extremum.everything.services.GetterService;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class EmployeeGetterService implements GetterService<Employee> {
    private final EmployeeService employeeService;

    public EmployeeGetterService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee get(String id) {
        return employeeService.get(id);
    }

    @Override
    public String getSupportedModel() {
        return Employee.MODEL_NAME;
    }
}

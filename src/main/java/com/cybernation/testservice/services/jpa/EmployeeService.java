package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.models.jpa.persistable.Employee;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.jpa.service.PostgresCommonService;

import java.util.UUID;

/**
 * @author rpuch
 */
public interface EmployeeService extends PostgresCommonService<Employee> {
    CollectionFragment<Employee> findByDepartmentId(UUID departmentId);
}

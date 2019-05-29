package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.extremum.everything.collection.CollectionFragment;
import com.extremum.jpa.services.PostgresCommonService;

import java.util.UUID;

/**
 * @author rpuch
 */
public interface EmployeeService extends PostgresCommonService<Employee> {
    CollectionFragment<Employee> findByDepartmentId(UUID departmentId);
}

package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Employee;
import com.extremum.common.service.PostgresCommonService;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
public interface EmployeeService extends PostgresCommonService<Employee> {
    List<Employee> findByDepartmentId(UUID departmentId);
}

package com.cybernation.testservice.dao.jpa;

import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.extremum.jpa.dao.PostgresCommonDao;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
public interface EmployeeDao extends PostgresCommonDao<Employee> {
    List<Employee> findByDepartmentId(UUID departmentId);

    long countByDepartmentId(UUID departmentId);
}

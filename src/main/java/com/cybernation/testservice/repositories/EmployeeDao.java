package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Employee;
import com.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
@Repository
public interface EmployeeDao extends SpringDataJpaCommonDao<Employee> {
    List<Employee> findByDepartmentId(UUID departmentId);

    long countByDepartmentId(UUID departmentId);
}

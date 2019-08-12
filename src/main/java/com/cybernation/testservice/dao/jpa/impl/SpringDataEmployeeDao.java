package com.cybernation.testservice.dao.jpa.impl;

import com.cybernation.testservice.dao.jpa.EmployeeDao;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import io.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataEmployeeDao extends EmployeeDao, SpringDataJpaCommonDao<Employee> {
}

package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Employee;
import com.cybernation.testservice.repositories.EmployeeDao;
import com.extremum.services.impl.PostgresCommonServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author rpuch
 */
@Service
public class EmployeeServiceImpl extends PostgresCommonServiceImpl<Employee> implements EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao dao) {
        super(dao);
        this.employeeDao = dao;
    }

    @Override
    public List<Employee> findByDepartmentId(UUID departmentId) {
        return employeeDao.findByDepartmentId(departmentId);
    }
}

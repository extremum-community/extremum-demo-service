package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Employee;
import com.cybernation.testservice.repositories.EmployeeDao;
import com.extremum.common.service.impl.PostgresCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class EmployeeServiceImpl extends PostgresCommonServiceImpl<Employee> implements EmployeeService {
    public EmployeeServiceImpl(EmployeeDao dao) {
        super(dao);
    }
}

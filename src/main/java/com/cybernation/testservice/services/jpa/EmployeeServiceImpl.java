package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.EmployeeDao;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import io.extremum.everything.collection.CollectionFragment;
import io.extremum.jpa.services.impl.PostgresCommonServiceImpl;
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
    public CollectionFragment<Employee> findByDepartmentId(UUID departmentId) {
        List<Employee> employees = employeeDao.findByDepartmentId(departmentId);
        long total = employeeDao.countByDepartmentId(departmentId);
        return CollectionFragment.forFragment(employees, total);
    }
}

package com.cybernation.testservice.services;

import com.cybernation.testservice.models.Department;
import com.cybernation.testservice.repositories.DepartmentDao;
import com.extremum.services.impl.PostgresCommonServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class DepartmentServiceImpl extends PostgresCommonServiceImpl<Department> implements DepartmentService {
    public DepartmentServiceImpl(DepartmentDao dao) {
        super(dao);
    }
}

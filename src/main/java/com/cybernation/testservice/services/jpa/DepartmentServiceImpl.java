package com.cybernation.testservice.services.jpa;

import com.cybernation.testservice.dao.jpa.DepartmentDao;
import com.cybernation.testservice.models.jpa.persistable.Department;
import com.extremum.jpa.services.impl.PostgresCommonServiceImpl;
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
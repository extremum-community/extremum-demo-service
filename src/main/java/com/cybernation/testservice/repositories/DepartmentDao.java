package com.cybernation.testservice.repositories;

import com.cybernation.testservice.models.Department;
import com.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;


/**
 * @author rpuch
 */
@Repository
public interface DepartmentDao extends SpringDataJpaCommonDao<Department> {
}

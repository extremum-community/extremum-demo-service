package com.cybernation.testservice.dao.jpa;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;


/**
 * @author rpuch
 */
@Repository
public interface DepartmentDao extends SpringDataJpaCommonDao<Department> {
}

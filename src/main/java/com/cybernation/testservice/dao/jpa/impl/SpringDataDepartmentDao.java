package com.cybernation.testservice.dao.jpa.impl;

import com.cybernation.testservice.dao.jpa.DepartmentDao;
import com.cybernation.testservice.models.jpa.persistable.Department;
import com.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;


/**
 * @author rpuch
 */
@Repository
interface SpringDataDepartmentDao extends DepartmentDao, SpringDataJpaCommonDao<Department> {
}

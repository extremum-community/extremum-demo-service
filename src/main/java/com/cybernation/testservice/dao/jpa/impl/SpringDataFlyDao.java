package com.cybernation.testservice.dao.jpa.impl;

import com.cybernation.testservice.dao.jpa.FlyDao;
import com.cybernation.testservice.models.jpa.basic.Fly;
import io.extremum.jpa.dao.impl.SpringDataJpaCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataFlyDao extends FlyDao, SpringDataJpaCommonDao<Fly> {
}

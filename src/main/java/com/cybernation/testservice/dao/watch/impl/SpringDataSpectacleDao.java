package com.cybernation.testservice.dao.watch.impl;

import com.cybernation.testservice.dao.watch.SpectacleDao;
import com.cybernation.testservice.models.watch.Spectacle;
import io.extremum.mongo.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface SpringDataSpectacleDao extends SpectacleDao, SpringDataMongoCommonDao<Spectacle> {
}

package com.cybernation.testservice.dao.auth.impl;

import com.cybernation.testservice.dao.auth.TopSecretDao;
import com.cybernation.testservice.models.auth.TopSecret;
import io.extremum.mongo.dao.impl.SpringDataMongoCommonDao;
import org.springframework.stereotype.Repository;

/**
 * @author rpuch
 */
@Repository
interface TopSecretDaoImpl extends TopSecretDao, SpringDataMongoCommonDao<TopSecret> {
}

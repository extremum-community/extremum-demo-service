package com.cybernation.testservice.models.auth;

import io.extremum.common.models.MongoCommonModel;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rpuch
 */
@ModelName(TopSecret.MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = "ROLE_TOP_SECRET")
@Getter
@Setter
@NoDataSecurity
public class TopSecret extends MongoCommonModel {
    public static final String MODEL_NAME = "TopSecret";

    private String secret;
}

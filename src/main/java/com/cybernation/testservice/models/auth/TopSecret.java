package com.cybernation.testservice.models.auth;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.security.Access;
import com.extremum.security.ExtremumRequiredRoles;
import com.extremum.security.NoDataSecurity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rpuch
 */
@ModelName(TopSecret.MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = @Access("ROLE_TOP_SECRET"))
@Getter
@Setter
@NoDataSecurity
public class TopSecret extends MongoCommonModel {
    public static final String MODEL_NAME = "TopSecret";

    private String secret;
}

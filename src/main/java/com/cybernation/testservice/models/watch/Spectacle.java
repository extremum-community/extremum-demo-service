package com.cybernation.testservice.models.watch;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.security.ExtremumRequiredRoles;
import com.extremum.security.NoDataSecurity;
import com.extremum.sharedmodels.annotation.CapturedModel;
import io.extremum.authentication.api.RolesConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author rpuch
 */
@ModelName(Spectacle.MODEL_NAME)
@Document("spectacles")
@ExtremumRequiredRoles(defaultAccess = RolesConstants.ANONYMOUS)
@NoDataSecurity
@CapturedModel
@Getter @Setter @ToString
public class Spectacle extends MongoCommonModel {
    public static final String MODEL_NAME = "Spectacle";

    private String name;
}

package com.cybernation.testservice.models.watch;

import io.extremum.authentication.api.Roles;
import io.extremum.common.models.MongoCommonModel;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import io.extremum.watch.annotation.CapturedModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author rpuch
 */
@ModelName(Spectacle.MODEL_NAME)
@Document("spectacles")
@ExtremumRequiredRoles(defaultAccess = Roles.ANONYMOUS)
@NoDataSecurity
@CapturedModel
@Getter
@Setter
@ToString
public class Spectacle extends MongoCommonModel {
    public static final String MODEL_NAME = "Spectacle";

    private String name;
}

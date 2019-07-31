package com.cybernation.testservice.models.mongo;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.everything.security.Access;
import com.extremum.everything.security.EverythingRequiredRoles;
import com.extremum.everything.security.NoDataSecurity;
import io.extremum.authentication.api.RolesConstants;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("testMongoModels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ModelName(DemoMongoModel.MODEL_NAME)
@EverythingRequiredRoles(defaultAccess = @Access(RolesConstants.ANONYMOUS))
@NoDataSecurity
public class DemoMongoModel extends MongoCommonModel {
    public static final String MODEL_NAME = "DemoMongoModel";
    private String testId;
}

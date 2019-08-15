package com.cybernation.testservice.models.mongo;

import io.extremum.authentication.api.Roles;
import io.extremum.common.models.MongoCommonModel;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("testMongoModels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ModelName(DemoMongoModel.MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = Roles.ANONYMOUS)
@NoDataSecurity
public class DemoMongoModel extends MongoCommonModel {
    public static final String MODEL_NAME = "DemoMongoModel";
    private String testId;
}

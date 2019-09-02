package com.cybernation.testservice.models.mongo;

import io.extremum.authentication.api.Roles;
import io.extremum.common.model.annotation.ModelName;
import io.extremum.everything.collection.CollectionElementType;
import io.extremum.mongo.model.MongoCommonModel;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.cybernation.testservice.models.mongo.Street.MODEL_NAME;

/**
 * @author rpuch
 */
@Document(MODEL_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ModelName(MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = Roles.ANONYMOUS)
@NoDataSecurity
public class Street extends MongoCommonModel {
    public static final String MODEL_NAME = "Street";

    private String name;
    @CollectionElementType(House.class)
    private List<String> houses = new ArrayList<>();

    public enum FIELDS {
        name, houses
    }
}

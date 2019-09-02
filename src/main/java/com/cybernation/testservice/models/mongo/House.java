package com.cybernation.testservice.models.mongo;

import io.extremum.common.model.annotation.ModelName;
import io.extremum.mongo.model.MongoCommonModel;
import io.extremum.security.NoDataSecurity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.cybernation.testservice.models.mongo.House.MODEL_NAME;

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
@NoDataSecurity
public class House extends MongoCommonModel {
    public static final String MODEL_NAME = "House";
    private String number;

    public enum FIELDS {
        number
    }
}

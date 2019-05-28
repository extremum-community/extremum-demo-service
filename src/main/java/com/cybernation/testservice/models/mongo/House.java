package com.cybernation.testservice.models.mongo;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
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
public class House extends MongoCommonModel {
    public static final String MODEL_NAME = "House";
    private String number;

    public enum FIELDS {
        number
    }
}

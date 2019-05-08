package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author rpuch
 */
@Document(House.MODEL_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class House extends MongoCommonModel {
    public static final String MODEL_NAME = "House";

    private String number;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    public enum FIELDS {
        number
    }
}

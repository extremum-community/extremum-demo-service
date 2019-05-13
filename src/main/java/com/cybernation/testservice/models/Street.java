package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.everything.CollectionElementType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rpuch
 */
@Document(Street.MODEL_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Street extends MongoCommonModel {
    public static final String MODEL_NAME = "Street";

    private String name;
    @CollectionElementType(House.class)
    private List<String> houses = new ArrayList<>();

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    public enum FIELDS {
        name, houses
    }
}

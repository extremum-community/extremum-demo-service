package com.cybernation.testservice.models.mongo;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.everything.collection.CollectionElementType;
import com.extremum.everything.security.NoDataSecurity;
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

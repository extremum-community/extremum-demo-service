package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("testMongoModels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DemoMongoModel extends MongoCommonModel {
    @Transient
    public static final String MODEL_NAME = "DemoMongoModel";
    private String testId;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }
}

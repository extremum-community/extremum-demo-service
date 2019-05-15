package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import com.extremum.common.models.annotation.ModelName;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("testMongoModels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ModelName(DemoMongoModel.MODEL_NAME)
public class DemoMongoModel extends MongoCommonModel {
    public static final String MODEL_NAME = "DemoMongoModel";
    private String testId;
}

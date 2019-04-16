package com.cybernation.testservice.models;

import com.extremum.common.models.MongoCommonModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;

@Entity("testMongoModels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestMongoModel extends MongoCommonModel {
    private String testId;

    @Override
    public String getModelName() {
        return TestMongoModel.class.getSimpleName();
    }
}

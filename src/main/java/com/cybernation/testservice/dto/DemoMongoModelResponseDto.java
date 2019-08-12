package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.mongo.DemoMongoModel;
import io.extremum.sharedmodels.fundamental.CommonResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DemoMongoModelResponseDto extends CommonResponseDto {
    private String testId;

    @Override
    @JsonIgnore
    public String getModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}

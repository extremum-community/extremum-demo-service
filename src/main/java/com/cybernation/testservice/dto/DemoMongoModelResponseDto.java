package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.mongo.DemoMongoModel;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class DemoMongoModelResponseDto implements ResponseDto {
    private Descriptor id;
    private String testId;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    @Override
    @JsonIgnore
    public String getModel() {
        return DemoMongoModel.MODEL_NAME;
    }
}

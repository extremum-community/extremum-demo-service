package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.elasticsearch.RubberBand;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class RubberBandResponseDto implements ResponseDto {
    private Descriptor id;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    private String name;
    private String color;

    @Override
    @JsonIgnore
    public String getModel() {
        return RubberBand.MODEL_NAME;
    }
}

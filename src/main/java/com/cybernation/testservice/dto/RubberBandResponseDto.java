package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.elasticsearch.RubberBand;
import com.extremum.sharedmodels.fundamental.CommonResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RubberBandResponseDto extends CommonResponseDto {
    private String name;
    private String color;

    @Override
    @JsonIgnore
    public String getModel() {
        return RubberBand.MODEL_NAME;
    }
}

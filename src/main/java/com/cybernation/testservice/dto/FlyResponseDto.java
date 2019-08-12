package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.jpa.basic.Fly;
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
public class FlyResponseDto extends CommonResponseDto {
    private String name;

    @Override
    @JsonIgnore
    public String getModel() {
        return Fly.MODEL_NAME;
    }
}

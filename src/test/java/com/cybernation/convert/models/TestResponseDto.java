package com.cybernation.convert.models;

import io.extremum.sharedmodels.fundamental.CommonResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TestResponseDto extends CommonResponseDto {
    private String name;

    @Override
    public String getModel() {
        return "Test";
    }
}

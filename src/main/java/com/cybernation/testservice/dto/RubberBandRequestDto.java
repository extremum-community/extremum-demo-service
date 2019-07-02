package com.cybernation.testservice.dto;

import com.extremum.common.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RubberBandRequestDto implements RequestDto {
    private String name;
    private String color;
}

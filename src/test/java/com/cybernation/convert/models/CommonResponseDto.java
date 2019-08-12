package com.cybernation.convert.models;

import io.extremum.common.dto.AbstractResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommonResponseDto extends AbstractResponseDto {
    private String name;
}

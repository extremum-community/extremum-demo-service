package com.cybernation.testservice.dto;

import com.extremum.sharedmodels.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlyRequestDto implements RequestDto {
    private String name;
}

package com.cybernation.testservice.dto;

import com.extremum.common.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseRequestDto implements RequestDto {
    private String number;
}

package com.cybernation.testservice.dto;

import com.extremum.sharedmodels.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StreetRequestDto implements RequestDto {
    private String name;
    private List<String> houses;
}

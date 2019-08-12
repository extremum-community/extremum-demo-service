package com.cybernation.testservice.dto;

import io.extremum.sharedmodels.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentRequestDto implements RequestDto {
    private String name;
}

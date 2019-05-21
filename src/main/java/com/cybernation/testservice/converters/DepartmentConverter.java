package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.Department;
import com.cybernation.testservice.models.DepartmentRequestDto;
import com.cybernation.testservice.models.DepartmentResponseDto;
import com.cybernation.testservice.models.EmployeeResponseDto;
import com.extremum.common.collection.CollectionReference;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.FromRequestDtoConverter;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentConverter implements ToRequestDtoConverter<Department, DepartmentRequestDto>,
        ToResponseDtoConverter<Department, DepartmentResponseDto>, FromRequestDtoConverter<Department, DepartmentRequestDto> {
    private final EmployeeConverter employeeConverter;

    @Override
    public Department convertFromRequest(DepartmentRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Department department = new Department();
            department.setName(dto.getName());
            return department;
        }
    }

    @Override
    public DepartmentRequestDto convertToRequest(Department department, ConversionConfig conversionConfig) {
        DepartmentRequestDto dto = new DepartmentRequestDto();
        dto.setName(department.getName());
        return dto;
    }

    @Override
    public DepartmentResponseDto convertToResponse(Department department, ConversionConfig conversionConfig) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setCreated(department.getCreated());
        dto.setId(department.getUuid());
        dto.setModified(department.getModified());

        dto.setName(department.getName());
        List<EmployeeResponseDto> employees = department.getEmployees().stream()
                .map(employee -> employeeConverter.convertToResponse(employee, conversionConfig))
                .collect(Collectors.toList());
        dto.setEmployees(new CollectionReference<>(employees));
        dto.setCustomEmployees(new CollectionReference<>(employees));

        dto.setVersion(department.getVersion());
        return dto;
    }

    @Override
    public Class<? extends DepartmentRequestDto> getRequestDtoType() {
        return DepartmentRequestDto.class;
    }

    @Override
    public Class<? extends DepartmentResponseDto> getResponseDtoType() {
        return DepartmentResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Department.MODEL_NAME;
    }
}

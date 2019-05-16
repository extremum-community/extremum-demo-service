package com.cybernation.testservice.converters;

import com.cybernation.testservice.models.Employee;
import com.cybernation.testservice.models.EmployeeRequestDto;
import com.cybernation.testservice.models.EmployeeResponseDto;
import com.extremum.common.dto.converters.ConversionConfig;
import com.extremum.common.dto.converters.ToRequestDtoConverter;
import com.extremum.common.dto.converters.ToResponseDtoConverter;
import com.extremum.common.stucts.IdOrObjectStruct;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConverter implements ToRequestDtoConverter<Employee, EmployeeRequestDto>,
        ToResponseDtoConverter<Employee, EmployeeResponseDto> {

    public Employee convertFromRequest(EmployeeRequestDto dto) {
        if (dto == null) {
            return null;
        } else {
            Employee employee = new Employee();
            employee.setName(dto.getName());
            return employee;
        }
    }

    @Override
    public EmployeeRequestDto convertToRequest(Employee employee, ConversionConfig conversionConfig) {
        EmployeeRequestDto dto = new EmployeeRequestDto();
        dto.setName(employee.getName());
        return dto;
    }

    @Override
    public EmployeeResponseDto convertToResponse(Employee employee, ConversionConfig conversionConfig) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setCreated(employee.getCreated());
        dto.setId(employee.getUuid());
        dto.setModified(employee.getModified());
        dto.setName(employee.getName());
        dto.setVersion(employee.getVersion());
        dto.setDepartment(new IdOrObjectStruct<>(employee.getDepartment().getUuid()));
        return dto;
    }

    @Override
    public Class<? extends EmployeeRequestDto> getRequestDtoType() {
        return EmployeeRequestDto.class;
    }

    @Override
    public Class<? extends EmployeeResponseDto> getResponseDtoType() {
        return EmployeeResponseDto.class;
    }

    @Override
    public String getSupportedModel() {
        return Employee.MODEL_NAME;
    }

}

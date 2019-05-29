package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.extremum.common.stucts.IdOrObjectStruct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class EmployeeResponseDto implements ResponseDto {
    private Descriptor id;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    private String name;
    private IdOrObjectStruct<Descriptor, Department> department;

    @Override
    @JsonIgnore
    public String getModel() {
        return Employee.MODEL_NAME;
    }
}

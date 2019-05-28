package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.extremum.common.collection.CollectionReference;
import com.extremum.common.collection.conversion.OwnedCollection;
import com.extremum.common.descriptor.Descriptor;
import com.extremum.common.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
public class DepartmentResponseDto implements ResponseDto {
    private Descriptor id;
    private Long version;
    private ZonedDateTime created;
    private ZonedDateTime modified;

    private String name;
    @OwnedCollection
    private CollectionReference<EmployeeResponseDto> employees;
    @OwnedCollection
    private CollectionReference<EmployeeResponseDto> customEmployees;
    
    @Override
    @JsonIgnore
    public String getModel() {
        return Department.MODEL_NAME;
    }
}

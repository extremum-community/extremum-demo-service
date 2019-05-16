package com.cybernation.testservice.models;

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
    public Descriptor getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public ZonedDateTime getModified() {
        return modified;
    }

    @Override
    @JsonIgnore
    public String getModel() {
        return Employee.MODEL_NAME;
    }
}

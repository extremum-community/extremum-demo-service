package com.cybernation.testservice.dto;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.models.jpa.persistable.Employee;
import com.extremum.sharedmodels.basic.IdOrObject;
import com.extremum.sharedmodels.descriptor.Descriptor;
import com.extremum.sharedmodels.fundamental.CommonResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeResponseDto extends CommonResponseDto {
    private String name;
    private IdOrObject<Descriptor, Department> department;

    @Override
    @JsonIgnore
    public String getModel() {
        return Employee.MODEL_NAME;
    }
}

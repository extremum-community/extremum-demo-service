package com.cybernation.testservice.models;

import com.extremum.common.models.PostgresCommonModel;
import com.extremum.common.models.SoftDeletePostgresModel;
import com.extremum.common.models.annotation.ModelName;
import com.extremum.common.models.annotation.ModelRequestDto;
import com.extremum.common.models.annotation.ModelResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;



/**
 * @author rpuch
 */
@Entity
@Table(name = "department")
@Getter
@Setter
@ModelName(Department.MODEL_NAME)
@ModelRequestDto(DepartmentRequestDto.class)
@ModelResponseDto(DepartmentResponseDto.class)
public class Department extends PostgresCommonModel {
    public static final String MODEL_NAME = "Department";

    private String name;
    @Getter(onMethod_ = {@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)})
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}


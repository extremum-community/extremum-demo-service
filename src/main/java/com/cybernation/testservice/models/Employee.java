package com.cybernation.testservice.models;

import com.extremum.common.models.PostgresCommonModel;
import com.extremum.common.models.annotation.ModelName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rpuch
 */
@Entity
@Table(name = "employee")
@ModelName("Employee")
@Getter @Setter
public class Employee extends PostgresCommonModel {
    public static final String MODEL_NAME = "Employee";

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    private String name;
    @Getter(onMethod_ = {@ManyToOne(fetch = FetchType.LAZY), @JoinColumn(name = "department_id")})
    private Department department;
}

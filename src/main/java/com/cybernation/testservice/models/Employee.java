package com.cybernation.testservice.models;

import com.extremum.common.models.SoftDeletablePostgresCommonModel;
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
public class Employee extends SoftDeletablePostgresCommonModel {
    public static final String MODEL_NAME = "Employee";

    private String name;
    @Getter(onMethod_ = {@ManyToOne(fetch = FetchType.LAZY), @JoinColumn(name = "department_id")})
    private Department department;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }
}

package com.cybernation.testservice.models.jpa.persistable;

import io.extremum.common.models.annotation.ModelName;
import io.extremum.security.NoDataSecurity;
import io.extremum.jpa.models.SoftDeletePostgresModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rpuch
 */
@Entity
@Table(name = "employee")
@ModelName("Employee")
@Getter
@Setter
@NoDataSecurity
public class Employee extends SoftDeletePostgresModel {
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

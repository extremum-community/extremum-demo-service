package com.cybernation.testservice.models;

import com.extremum.common.models.SoftDeletablePostgresCommonModel;
import com.extremum.common.models.annotation.ModelName;
import lombok.Getter;
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
@ModelName(Department.MODEL_NAME)
@Getter @Setter
public class Department extends SoftDeletablePostgresCommonModel {
    public static final String MODEL_NAME = "Department";

    private String name;
    @Getter(onMethod_ = {@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)})
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}

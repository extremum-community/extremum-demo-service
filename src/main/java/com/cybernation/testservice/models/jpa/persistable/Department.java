package com.cybernation.testservice.models.jpa.persistable;

import io.extremum.authentication.api.constants.RolesConstants;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.jpa.models.PostgresCommonModel;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
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
@Getter
@Setter
@ModelName(Department.MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = RolesConstants.ANONYMOUS)
@NoDataSecurity
public class Department extends PostgresCommonModel {
    public static final String MODEL_NAME = "Department";
    public static final String CUSTOM_EMPLOYEES = "customEmployees";

    private String name;
    @Getter(onMethod_ = {@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)})
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}


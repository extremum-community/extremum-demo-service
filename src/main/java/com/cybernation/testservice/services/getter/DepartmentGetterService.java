package com.cybernation.testservice.services.getter;

import com.cybernation.testservice.models.jpa.persistable.Department;
import com.cybernation.testservice.services.jpa.DepartmentService;
import io.extremum.everything.services.GetterService;
import org.springframework.stereotype.Service;

/**
 * @author rpuch
 */
@Service
public class DepartmentGetterService implements GetterService<Department> {
    private final DepartmentService departmentService;

    public DepartmentGetterService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public Department get(String id) {
        return departmentService.get(id);
    }

    @Override
    public String getSupportedModel() {
        return Department.MODEL_NAME;
    }
}

package com.ims_team4.repository;

import com.ims_team4.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    List<Department> getAllDepartment();
}

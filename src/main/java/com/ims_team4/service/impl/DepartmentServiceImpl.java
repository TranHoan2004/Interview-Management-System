package com.ims_team4.service.impl;

import com.ims_team4.dto.DepartmentDTO;
import com.ims_team4.model.Department;
import com.ims_team4.repository.DepartmentRepository;
import com.ims_team4.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.getAllDepartment().stream()
                .map(dept -> new DepartmentDTO(dept.getId(), dept.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findByName(String name) {
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found: " + name));
        return new DepartmentDTO(department.getId(), department.getName());
    }
}

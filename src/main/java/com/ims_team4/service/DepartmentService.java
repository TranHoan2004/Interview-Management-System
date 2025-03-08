package com.ims_team4.service;

import com.ims_team4.dto.DepartmentDTO;
import com.ims_team4.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO findByName(String name); // ✅ Thêm method findByName
}


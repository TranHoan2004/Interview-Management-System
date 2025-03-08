package com.ims_team4.service;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.utils.HrRole;

import java.util.List;

public interface EmployeeService {
    // 🔹 Lấy danh sách tất cả nhân viên
    List<EmployeeDTO> findAll();

    // 🔹 Lấy danh sách nhân viên theo vai trò
    List<EmployeeDTO> findByPosition(String position);

    // 🔹 Thêm mới nhân viên từ EmployeeDTO
    void saveEmployee(EmployeeDTO employee);

    // 🔹 Cập nhật thông tin nhân viên
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employee);

    // 🔹 Xóa nhân viên theo ID
    void deleteById(Long id);

    List<EmployeeDTO> getAllEmployee();

    List<EmployeeDTO> getAllEmployeeByRole(HrRole role);

    EmployeeDTO getEmployeeById(int id);

}

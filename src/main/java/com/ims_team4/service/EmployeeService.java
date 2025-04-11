package com.ims_team4.service;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.Employee;
import com.ims_team4.model.utils.HrRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    // 🔹 Lấy danh sách tất cả nhân viên
    List<EmployeeDTO> findAll();

    // 🔹 Lấy danh sách nhân viên theo vai trò
    List<EmployeeDTO> findByPosition(String position);

    // 🔹 Thêm mới nhân viên từ EmployeeDTO
    void saveEmployee(EmployeeDTO employeeDTO, Long userId, Long deptId, Long posId);

    void updateEmployeesPassword(EmployeeDTO employee);

    // 🔹 Xóa nhân viên theo ID
    void deleteById(Long id);

    List<EmployeeDTO> getAllEmployee();

    List<EmployeeDTO> getAllEmployeeByRole(HrRole role);

    EmployeeDTO getEmployeeById(long id);

    Page<EmployeeDTO> search(String title, Long positionId, Pageable pageable);

    Optional<Employee> getEmployeeByUserId(Long userId);

    Optional<Employee> getDefaultEmployee();

    EmployeeDTO getEmployeeDTOByEmail(String email);

    EmployeeDTO getActiveEmployeesByID(Long id);
    List<EmployeeDTO> search(String title, Long positionId);


}

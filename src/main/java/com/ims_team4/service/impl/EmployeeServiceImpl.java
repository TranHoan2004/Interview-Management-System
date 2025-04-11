package com.ims_team4.service.impl;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.Employee;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.EmployeeRepository;
import com.ims_team4.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.ims_team4.utils.RandomCode.generateSixRandomCodes;

@Service
// TrangNT
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder encoder;
    private final Logger logger = java.util.logging.Logger.getLogger(EmployeeServiceImpl.class.getName());

    public EmployeeServiceImpl(BCryptPasswordEncoder encoder, EmployeeRepository employeeRepository) {
        this.encoder = encoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findByPosition(String position) {
        List<Employee> employees = employeeRepository.findByPositionName(position);
        return employees.stream()
                .map(this::convertToDTO) // Chuyển từng Employee -> EmployeeDTO
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDTO> search(String title, Long positionId, Pageable pageable) {
        Page<Employee> employeePage;

        if ((title != null && !title.isEmpty()) && positionId != null) {
            employeePage = employeeRepository.findByUserInAndPositionId(title, positionId, pageable);
        } else if (title != null && !title.isEmpty()) {
            employeePage = employeeRepository.findByName(title, pageable);
        } else if (positionId != null) {
            employeePage = employeeRepository.findByPositionId(positionId, pageable);
        } else {
            employeePage = employeeRepository.findAll(pageable);
        }

        return employeePage.map(this::convertToDTO);
    }


    @Override
    @Transactional
    public void saveEmployee(@NotNull EmployeeDTO employeeDTO, Long userId, Long deptId, Long posId) {
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getUserID()).orElse(null);

        if (existingEmployee == null) {
            // Employee chưa tồn tại => INSERT bằng HQL
            employeeRepository.insertEmployee(
                    userId,
                    deptId,
                    posId,
                    encoder.encode(employeeDTO.getPassword()),
                    generateSixRandomCodes(),
                    employeeDTO.getRole().toString()
            );
        } else {
            // Employee đã tồn tại => UPDATE bằng HQL
            // Chuyển từ String thành Enum

            employeeRepository.updateEmployee(existingEmployee.getUser().getId(), userId, deptId, posId, employeeDTO.getRole(), employeeDTO.getPassword());
        }
    }

    @Override
    public void updateEmployeesPassword(@NotNull EmployeeDTO employee) {
        String password = employee.getPassword();
        Employee emp = employeeRepository.findById(employee.getUserID()).get();
        emp.setPassword(encoder.encode(password));
        employeeRepository.save(emp);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot delete. Employee not found with ID: " + id));
        employee.setDepartment(null);
        employee.setPosition(null);
        // Xóa Employee (do CascadeType.ALL, User cũng bị xóa)
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAllEmployees().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getAllEmployeeByRole(HrRole role) {
        return employeeRepository.findAllEmployeesByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(long id) {
        Optional<Employee> list = employeeRepository.findById(id);
        if (list.isPresent()) {
            return convertToDTO(list.get());
        }
        throw new EntityNotFoundException("Employee not found with ID: " + id);
    }

    private EmployeeDTO convertToDTO(@NotNull Employee employee) {
        byte[] avatarBytes = employee.getUser().getAvatar();
        String avatar = avatarBytes != null ? new String(avatarBytes) : "";
        return EmployeeDTO.builder()
                .userID(employee.getId())
                .dob(employee.getUser().getDob())
                .gender(employee.getUser().getGender())
                .address(employee.getUser().getAddress())
                .password(employee.getPassword())
                .fullname(employee.getUser().getFullname())
                .email(employee.getUser().getEmail())
                .phone(employee.getUser().getPhone())
                .positionId((employee.getPosition().getId()))
                .positionName(employee.getPosition().getName())
                .departmentName(employee.getDepartment().getName())
                .role(employee.getRole())
                .status(employee.getUser().isStatus())
                .workingName(employee.getWorkingName())
                .dob(employee.getUser().getDob())
                .avatar(avatar)
                .build();
    }

    @Override
    public Optional<Employee> getEmployeeByUserId(Long userId) {
        Optional<Employee> employee = employeeRepository.findByUserId(userId);

        if (employee.isEmpty()) {
            logger.severe("❌ Employee NOT FOUND for User ID: " + userId);
        } else {
            logger.info("✅ Employee FOUND: " + employee.get().getUser().getFullname());
        }

        return employee;
    }

    @Override
    public Optional<Employee> getDefaultEmployee() {
        return employeeRepository.findDefaultEmployee();
    }

    @Override
    public EmployeeDTO getEmployeeDTOByEmail(String email) {
        return convertToDTO(employeeRepository.findByEmail(email));
    }

    @Override
    public EmployeeDTO getActiveEmployeesByID(Long id) {
        return convertToDTO(employeeRepository.getActiveEmployees(id));
    }

    @Override
    public List<EmployeeDTO> search(String title, Long positionId) {
        List<Employee> employees;

        if ((title != null && !title.isEmpty()) && positionId != null) {
            employees = employeeRepository.findByUserInAndPositionId(title, positionId);
        } else if (title != null && !title.isEmpty()) {
            employees = employeeRepository.findByName(title);
        } else if (positionId != null) {
            employees = employeeRepository.findByPositionId(positionId);
        } else {
            employees = employeeRepository.findAll();
        }

        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

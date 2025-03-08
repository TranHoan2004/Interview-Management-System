package com.ims_team4.service.impl;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.Department;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Position;
import com.ims_team4.model.User;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.DepartmentRepository;
import com.ims_team4.repository.EmployeeRepository;
import com.ims_team4.repository.PositionRepository;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public EmployeeServiceImpl(BCryptPasswordEncoder encoder, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PositionRepository positionRepository, UserRepository userRepository) {
        this.encoder = encoder;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
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
    public void saveEmployee(@NotNull EmployeeDTO employeeDTO) {
        // Lấy thông tin Department & Position từ database
        Department dept = departmentRepository.findByName(employeeDTO.getDepartmentName())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Position position = positionRepository.findByName(employeeDTO.getDepartmentName())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        String password = employeeDTO.getPassword();
        employeeDTO.setPassword(encoder.encode(password));

        // Gọi hàm mapper (truyền thêm department & position)
        Employee employee = convert(employeeDTO, dept, position);

        // Lưu vào database
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, @NotNull EmployeeDTO employeeDTO) {
        // Tìm Employee từ ID
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        // Cập nhật thông tin User
        User user = existingEmployee.getUser();
        // user.setDob(employeeDTO.getUser().getDob());
        // user.setGender(employeeDTO.getUser().getGender());
        // user.setEmail(employeeDTO.getUser().getEmail());
        // user.setAddress(employeeDTO.getUser().getAddress());
        // user.setFullname(employeeDTO.getUser().getFullname());
        // user.setPhone(employeeDTO.getUser().getPhone());
        // user.setRole(employeeDTO.getUser().getRole());

        // Lưu User vào database
        userRepository.save(user); // 🔥 Fix lỗi thiếu lưu User

        // Kiểm tra & lấy Department & Position từ DB
        // Department dept =
        // departmentRepository.findByName(employeeDTO.getDepartment().getName())
        // .orElseThrow(() -> new RuntimeException("Department not found"));

        // Position position =
        // positionRepository.findByName(employeeDTO.getPosition().getName())
        // .orElseThrow(() -> new RuntimeException("Position not found"));

        // Cập nhật thông tin Employee
        existingEmployee.setPassword(employeeDTO.getPassword());
        // existingEmployee.setPosition(position);
        // existingEmployee.setDepartment(dept);

        Employee savedEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(savedEmployee);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot delete. Employee not found with ID: " + id));

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
    public EmployeeDTO getEmployeeById(int id) {
        Optional<Employee> list = employeeRepository.findById((long) id);
        if (list.isPresent()) {
            return convertToDTO(list.get());
        }
        throw new EntityNotFoundException("Employee not found with ID: " + id);
    }

    private EmployeeDTO convertToDTO(@NotNull Employee employee) {
        return EmployeeDTO.builder()
                .user(employee.getId())
                .password(employee.getPassword())
                .fullname(employee.getUser().getFullname())
                .email(employee.getUser().getEmail())
                .phone(employee.getUser().getPhone())
                .positionId(Math.toIntExact(employee.getPosition().getId()))
                .positionName(employee.getPosition().getName())
                .departmentName(employee.getDepartment().getName())
                .interviewID(employee.getInterview().getId())
                .role(employee.getRole())
                .status(employee.getUser().isStatus())
                .build();
    }


    private Employee convert(@NotNull EmployeeDTO dto, Department department, Position position) {
        // if (dto == null)
        // return null;
        // User user = User.builder()
        // .id(dto.getUser().getId()) // Nếu ID null, tạo User mới
        // .dob(dto.getUser().getDob())
        // .gender(dto.getUser().getGender())
        // .email(dto.getUser().getEmail())
        // .address(dto.getUser().getAddress())
        // .fullname(dto.getUser().getFullname())
        // .phone(dto.getUser().getPhone())
        // .role(dto.getUser().getRole())
        // .build();

        // Employee employee = Employee.builder()
        // .id(dto.getUser().getId())
        // .password(dto.getPassword())
        // .position(position)
        // .department(department)
        // .build();
        // return employee;
        return Employee.builder()
                .id(dto.getUser())
                .password(dto.getPassword())
                .role(dto.getRole())
                .position(position)
                .department(department)
                .build();
    }

}

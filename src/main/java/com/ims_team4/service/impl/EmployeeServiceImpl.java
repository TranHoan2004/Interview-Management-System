package com.ims_team4.service.impl;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.Department;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Position;
import com.ims_team4.model.Users;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.DepartmentRepository;
import com.ims_team4.repository.EmployeeRepository;
import com.ims_team4.repository.PositionRepository;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.DepartmentService;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.PositionService;
import com.ims_team4.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ims_team4.utils.RandomCode.generateSixRandomCodes;
import static com.ims_team4.utils.webSocket.InsertImageToMySQL.avatarValues;

@Service
// TrangNT
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public EmployeeServiceImpl(BCryptPasswordEncoder encoder, EmployeeRepository employeeRepository,
                               DepartmentService departmentService, PositionService positionService, UserService userService) {
        this.encoder = encoder;
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.userService = userService;
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
    public List<EmployeeDTO> search(String title, Long positionId) {
        // Trường hợp 1: Có cả title và positionId -> Tìm theo cả hai
        if ((title != null && !title.isEmpty()) && positionId != null) {
            List<Employee> employees = employeeRepository.findByUserInAndPositionId(title, positionId);
            return employees.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        // Trường hợp 2: Có title -> Tìm theo title
        else if (title != null && !title.isEmpty()) {
            List<Employee> employees = employeeRepository.findByName(title);
            return employees.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        // Trường hợp 3: Có positionId -> Tìm theo positionId
        else if (positionId != null) {
            List<Employee> employees = employeeRepository.findByPositionId(positionId);
            return employees.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        // Trường hợp 4: Không có điều kiện nào -> Trả về tất cả nhân viên
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void saveEmployee(@NotNull EmployeeDTO employeeDTO) {
        // Kiểm tra xem Employee đã tồn tại chưa
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getUserID()).orElse(null);

        if (existingEmployee == null) {
            // Employee chưa tồn tại => INSERT
            existingEmployee = Employee.builder()
                    .user(userService.getUser(employeeDTO.getUserID()))
                    .department(departmentService.findById(employeeDTO.getDepartmentId()))
                    .position(positionService.findById(employeeDTO.getPositionId()))
                    .password(encoder.encode("123"))
                    .workingName(generateSixRandomCodes())
                    .role(employeeDTO.getRole())
                    .build();
        } else {
            // Employee đã tồn tại => UPDATE
            existingEmployee.setDepartment(departmentService.findById(employeeDTO.getDepartmentId()));
            existingEmployee.setPosition(positionService.findById(employeeDTO.getPositionId()));
            existingEmployee.setRole(employeeDTO.getRole());
        }

        // Lưu vào database (có thể là INSERT hoặc UPDATE)
        employeeRepository.save(existingEmployee);
    }


    @Override
    public EmployeeDTO updateEmployee(Long id, @NotNull EmployeeDTO employeeDTO) {
        // Tìm Employee từ ID
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        // Cập nhật thông tin User
//        Users user = existingEmployee.getUser();
        // user.setDob(employeeDTO.getUser().getDob());
        // user.setGender(employeeDTO.getUser().getGender());
        // user.setEmail(employeeDTO.getUser().getEmail());
        // user.setAddress(employeeDTO.getUser().getAddress());
        // user.setFullname(employeeDTO.getUser().getFullname());
        // user.setPhone(employeeDTO.getUser().getPhone());
        // user.setRole(employeeDTO.getUser().getRole());


//         Kiểm tra & lấy Department & Position từ DB
        Department dept = departmentService.findById(employeeDTO.getDepartmentId());

        Position position =  positionService.findById(employeeDTO.getPositionId());

        // Cập nhật thông tin Employee
        existingEmployee.setPassword(employeeDTO.getPassword());
        existingEmployee.setPosition(position);
        existingEmployee.setRole(employeeDTO.getRole());
        existingEmployee.setDepartment(dept);

        Employee savedEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(savedEmployee);

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
        System.out.println(avatar);
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

//    private Employee convert(@NotNull EmployeeDTO dto, Department department, Position position) {
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
//        return Employee.builder()
//                .user(dto.getUser())
//                .password(dto.getPassword())
//                .role(dto.getRole())
//                .position(position)
//                .department(department)
//                .build();
//    }

}

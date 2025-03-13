package com.ims_team4.repository;

import com.ims_team4.model.Employee;
import com.ims_team4.model.Users;
import com.ims_team4.model.utils.HrRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // 🔹 Tìm Employee theo ID (đã có sẵn từ JpaRepository)
    @NotNull
    @Query("FROM Employee e WHERE e.id=:id")
    Optional<Employee> findById(@NotNull Long id);

    // 🔹 Tìm Employee theo User
    Optional<Employee> findByUser(Users user);

    // 🔹 Lấy danh sách Employee theo chức vụ (Position)
    @Query("SELECT e FROM Employee e WHERE e.position.name = :positionName")
    List<Employee> findByPositionName(@Param("positionName") String positionName);

    // 🔹 Kiểm tra Employee có tồn tại theo ID
    boolean existsById(@NotNull Long id);

    // 🔹 Lấy danh sách tất cả Employee
    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    // 🔹 Lấy danh sách Employee theo vai trò (Role)
    @Query("SELECT e FROM Employee e WHERE e.role = :role")
    List<Employee> findAllEmployeesByRole(@Param("role") HrRole role);
}


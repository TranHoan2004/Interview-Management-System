package com.ims_team4.repository;

import com.ims_team4.model.Employee;
import com.ims_team4.model.Users;
import com.ims_team4.model.utils.HrRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT e FROM Employee e WHERE e.role = 'ROLE_RECRUITER' ORDER BY e.id ASC LIMIT 1")
    Optional<Employee> findDefaultEmployee();

    @Query("SELECT e FROM Employee e WHERE e.user.id = :userId")
    Optional<Employee> findByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Employee e WHERE e.position.id = :positionId")
    List<Employee> findByPositionId(@Param("positionId") long positionId);

    @Query(value = """
            SELECT e.* 
            FROM employee e
            JOIN users u ON e.id = u.id  -- Employee.id chính là Users.id
            WHERE (:title IS NULL OR u.fullname LIKE CONCAT('%', :title, '%')) 
            AND (:positionId IS NULL OR e.position_id = :positionId)
            """, nativeQuery = true)
    List<Employee> findByUserInAndPositionId(@Param("title") String title, @Param("positionId") Long positionId);

    @Query("SELECT e FROM Employee e JOIN e.user u WHERE u.fullname LIKE %:fullname%")
    List<Employee> findByName(@Param("fullname") String fullname);

    @Modifying
    @Query(value = "INSERT INTO employee (id, department_id, position_id, password, working_name, role) " +
            "VALUES (:userId, :deptId, :posId, :password, :workingName, :role)", nativeQuery = true)
    void insertEmployee(@Param("userId") Long userId,
                        @Param("deptId") Long deptId,
                        @Param("posId") Long posId,
                        @Param("password") String password,
                        @Param("workingName") String workingName,
                        @Param("role") String role);

    @Modifying
    @Query("UPDATE Employee e SET e.user = (SELECT u FROM Users u WHERE u.id = :userId), " +
            "e.department.id = :deptId, e.position.id = :posId, e.role = :role, e.password = :password " +
            "WHERE e.user.id = :oldUserId")
    void updateEmployee(@Param("oldUserId") Long oldUserId,
                        @Param("userId") Long userId,
                        @Param("deptId") Long deptId,
                        @Param("posId") Long posId,
                        @Param("role") HrRole role,
                        @Param("password") String password);
    @Query("SELECT e FROM Employee e JOIN e.user u WHERE u.status = true")
    List<Employee> getActiveEmployees();

    @Query("from Employee e where e.user.email=:email")
    Employee findByEmail(@Param("email") String email);
}


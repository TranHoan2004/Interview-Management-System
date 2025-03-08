package com.ims_team4.repository;

import com.ims_team4.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name); // ✅ Tìm phòng ban theo tên
    List<Department> getAllDepartment();
}


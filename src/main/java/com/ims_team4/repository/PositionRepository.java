package com.ims_team4.repository;

import com.ims_team4.model.Department;
import com.ims_team4.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> getAllPosition();
    Optional<Position> findByName(String name);
}

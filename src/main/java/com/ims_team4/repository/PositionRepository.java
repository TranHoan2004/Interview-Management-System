package com.ims_team4.repository;

import com.ims_team4.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> getAllPosition();
}

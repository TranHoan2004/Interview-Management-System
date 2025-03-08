package com.ims_team4.repository;

import com.ims_team4.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findByNameIn(Set<String> names);
}

package com.ims_team4.repository;

import com.ims_team4.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface LevelRepository extends JpaRepository<Level, Long> {

    @Query("SELECT l FROM Level l")
    List<Level> getAllLevel();

    List<Level> findByNameIn(Set<String> names);

    Set<Level> findByIdIn(Set<Long> ids);
}

package com.ims_team4.repository;

import com.ims_team4.model.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LevelRepository extends CrudRepository<Level, Long> {
    List<Level> getAllLevel();
}

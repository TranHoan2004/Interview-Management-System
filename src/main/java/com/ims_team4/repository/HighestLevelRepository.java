package com.ims_team4.repository;

import com.ims_team4.model.HighestLevel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HighestLevelRepository extends CrudRepository<HighestLevel, Integer> {
    List<HighestLevel> getAllHighestLevel();
}

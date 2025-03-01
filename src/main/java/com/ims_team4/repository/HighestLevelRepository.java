package com.ims_team4.repository;

import com.ims_team4.model.HighestEducation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HighestLevelRepository extends CrudRepository<HighestEducation, Integer> {
    List<HighestEducation> getAllHighestLevel();
}

package com.ims_team4.repository;

import com.ims_team4.model.HighestLevel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HighestLevelRepository extends CrudRepository<HighestLevel, Integer> {
    List<HighestLevel> getAllHighestLevel();




    Optional<HighestLevel> findByIdCustom(@NotNull Long id);
}

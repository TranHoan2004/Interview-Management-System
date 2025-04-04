package com.ims_team4.service;

import com.ims_team4.dto.HighestLevelDTO;
import com.ims_team4.model.HighestLevel;

import java.util.List;
import java.util.Optional;

public interface HighestLevelService {
    List<HighestLevelDTO> getAllHighestLevel();

    Optional<HighestLevel> getHighestLevelById(long id);
}

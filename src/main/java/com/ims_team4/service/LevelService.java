package com.ims_team4.service;

import com.ims_team4.dto.LevelDTO;
import com.ims_team4.model.Level;

import java.util.List;
import java.util.Set;

public interface LevelService {
    List<LevelDTO> getAllLevels();

    Set<Level> getLevelsByName(List<String> levelNames);
}

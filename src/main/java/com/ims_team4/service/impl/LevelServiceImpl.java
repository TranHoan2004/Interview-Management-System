package com.ims_team4.service.impl;

import com.ims_team4.dto.LevelDTO;
import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
import com.ims_team4.service.LevelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<LevelDTO> getAllLevels() {
        return levelRepository.getAllLevel().stream()
                .map(level -> new LevelDTO(level.getId(), level.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Level> getLevelsByName(List<String> levelNames) {
        if (levelNames == null || levelNames.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> levelNamesSet = new HashSet<>(levelNames);
        List<Level> levels = levelRepository.findByNameIn(levelNamesSet);
        return new HashSet<>(levels != null ? levels : Collections.emptyList());

    }

    private LevelDTO convertToDTO(@NotNull Level Level) {
        return LevelDTO.builder()
                .id(Level.getId())
                .name(Level.getName())
                .build();
    }
}

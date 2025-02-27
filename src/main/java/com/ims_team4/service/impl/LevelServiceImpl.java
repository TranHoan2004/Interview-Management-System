package com.ims_team4.service.impl;

import com.ims_team4.dto.LevelDTO;
import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
import com.ims_team4.service.LevelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<LevelDTO> getAllLevel() {
        return levelRepository.getAllLevel().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LevelDTO convertToDTO(@NotNull Level Level) {
        return LevelDTO.builder()
                .id(Level.getId())
                .name(Level.getName())
                .build();
    }
}

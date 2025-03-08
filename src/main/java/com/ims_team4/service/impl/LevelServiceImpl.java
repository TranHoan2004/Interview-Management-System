package com.ims_team4.service.impl;

import com.ims_team4.dto.LevelDTO;
import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
import com.ims_team4.service.LevelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;


    @Override
    public List<LevelDTO> getAllLevels() {
        return levelRepository.findAll().stream()
                .map(level -> new LevelDTO(level.getId(), level.getName()))
                .collect(Collectors.toList());
    }

    private LevelDTO convertToDTO(@NotNull Level Level) {
        return LevelDTO.builder()
                .id(Level.getId())
                .name(Level.getName())
                .build();
    }
}

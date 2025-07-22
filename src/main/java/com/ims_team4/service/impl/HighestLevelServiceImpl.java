package com.ims_team4.service.impl;

import com.ims_team4.dto.HighestLevelDTO;
import com.ims_team4.model.HighestLevel;
import com.ims_team4.repository.HighestLevelRepository;
import com.ims_team4.service.HighestLevelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HighestLevelServiceImpl implements HighestLevelService {
    private final HighestLevelRepository highestLevelRepository;

    public HighestLevelServiceImpl(HighestLevelRepository highestLevelRepository) {
        this.highestLevelRepository = highestLevelRepository;
    }

    @Override
    public List<HighestLevelDTO> getAllHighestLevel() {
        return highestLevelRepository.getAllHighestLevel().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HighestLevelDTO convertToDTO(@NotNull HighestLevel highestLevel) {
        return HighestLevelDTO.builder()
                .id(highestLevel.getId())
                .name(highestLevel.getName())
                .build();
    }

    @Override
    public Optional<HighestLevel> getHighestLevelById(long id) {
        return highestLevelRepository.findByIdCustom(id); // ✅ Gọi repository đúng cách
    }
}

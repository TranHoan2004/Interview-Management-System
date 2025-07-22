package com.ims_team4.service.impl;

import com.ims_team4.dto.PositionDTO;
import com.ims_team4.model.Position;
import com.ims_team4.repository.PositionRepository;
import com.ims_team4.service.PositionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<PositionDTO> getAllPosition() {
        return positionRepository.getAllPosition().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Position findById(long id) {
        return positionRepository.getPosById(id);
    }

    private PositionDTO convertToDTO(@NotNull Position position) {
        return PositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }

    @Override
    public Position getPositionById(Long id) {
        return positionRepository.getPosById(id);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.getAllPosition();  // ✅ Dùng lại phương thức có sẵn

    }
}

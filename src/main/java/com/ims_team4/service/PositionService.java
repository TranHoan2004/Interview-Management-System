package com.ims_team4.service;

import com.ims_team4.dto.PositionDTO;
import com.ims_team4.model.Position;

import java.util.List;

public interface PositionService {
    List<PositionDTO> getAllPosition();
    Position findById(long id);
}

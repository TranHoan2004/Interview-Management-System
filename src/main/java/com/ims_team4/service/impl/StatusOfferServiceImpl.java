package com.ims_team4.service.impl;

import com.ims_team4.dto.StatusOfferDTO;
import com.ims_team4.model.StatusOffer;
import com.ims_team4.repository.StatusOfferRepository;
import com.ims_team4.service.StatusOfferService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusOfferServiceImpl implements StatusOfferService {
    private final StatusOfferRepository statusOfferRepository;

    public StatusOfferServiceImpl(StatusOfferRepository statusOfferRepository) {
        this.statusOfferRepository = statusOfferRepository;
    }

    @Override
    public List<StatusOfferDTO> getStatusOffer() {
        return statusOfferRepository.getAllStatusOffer().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private StatusOfferDTO convertToDTO(@NotNull StatusOffer statusOffer) {
        return StatusOfferDTO.builder()
                .id(statusOffer.getId())
                .statusName(statusOffer.getStatusName())
                .build();
    }
}

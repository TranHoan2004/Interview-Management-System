package com.ims_team4.service.impl;

import com.ims_team4.dto.BenefitDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.repository.BenefitRepository;
import com.ims_team4.service.BenefitService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;


    @Override
    public List<BenefitDTO> getAllBenefit() {
        return benefitRepository.getAllBenefit().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BenefitDTO convertToDTO(@NotNull Benefit benefit) {
        return BenefitDTO.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .build();
    }

    private Benefit convertEntity(@NotNull BenefitDTO benefit) {
        return Benefit.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .build();
    }
}

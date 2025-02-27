package com.ims_team4.service.impl;

import com.ims_team4.dto.BenefitDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.repository.BenefitRepository;
import com.ims_team4.service.BenefitService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<BenefitDTO> getAllBenefit() {
        return List.of();
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

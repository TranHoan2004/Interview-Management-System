package com.ims_team4.service.impl;

import com.ims_team4.dto.BenefitDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.repository.BenefitRepository;
import com.ims_team4.service.BenefitService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BenefitServiceImpl implements BenefitService {
    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<BenefitDTO> getAllBenefit() {
        return benefitRepository.getAllBenefit().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Benefit> getBenefitsByName(List<String> benefitNames) {
        if (benefitNames == null || benefitNames.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> benefitNamesSet = new HashSet<>(benefitNames);
        List<Benefit> benefits = benefitRepository.findByNameIn(benefitNamesSet);
        return new HashSet<>(benefits != null ? benefits : Collections.emptyList());

    }

    private BenefitDTO convertToDTO(@NotNull Benefit benefit) {
        return BenefitDTO.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .build();
    }
}

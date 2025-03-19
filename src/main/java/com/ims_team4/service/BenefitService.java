package com.ims_team4.service;

import com.ims_team4.dto.BenefitDTO;
import com.ims_team4.model.Benefit;

import java.util.List;
import java.util.Set;

public interface BenefitService {
    List<BenefitDTO> getAllBenefit();

    Set<Benefit> getBenefitsByName(List<String> benefitNames);
}

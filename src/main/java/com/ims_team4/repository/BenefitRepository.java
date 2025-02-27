package com.ims_team4.repository;

import com.ims_team4.model.Benefit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BenefitRepository extends CrudRepository<Benefit, Long> {
    List<Benefit> getAllBenefit();
}

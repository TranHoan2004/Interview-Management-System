package com.ims_team4.repository;

import com.ims_team4.model.Benefit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface BenefitRepository extends CrudRepository<Benefit, Long> {
    List<Benefit> getAllBenefit();

    List<Benefit> findByNameIn(Set<String> names);

    Set<Benefit> findByIdIn(Set<Long> ids);

}

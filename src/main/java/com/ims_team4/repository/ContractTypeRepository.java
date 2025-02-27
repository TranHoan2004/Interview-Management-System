package com.ims_team4.repository;

import com.ims_team4.model.ContractType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContractTypeRepository extends CrudRepository<ContractType, Long> {
    List<ContractType> getAllContractType();
}

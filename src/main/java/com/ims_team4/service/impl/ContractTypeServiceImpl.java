package com.ims_team4.service.impl;

import com.ims_team4.dto.ContractTypeDTO;
import com.ims_team4.model.ContractType;
import com.ims_team4.repository.ContractTypeRepository;
import com.ims_team4.service.ContractTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeRepository contractTypeRepository;

    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository) {
        this.contractTypeRepository = contractTypeRepository;
    }

    @Override
    public List<ContractTypeDTO> getAllContractType() {
        return contractTypeRepository.getAllContractType().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ContractTypeDTO convertToDTO(ContractType contractType) {
        return ContractTypeDTO.builder()
                .id(contractType.getId())
                .name(contractType.getName())
                .build();
    }
}

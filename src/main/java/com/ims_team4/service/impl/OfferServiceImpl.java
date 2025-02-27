package com.ims_team4.service.impl;

import com.ims_team4.dto.OfferDTO;
import com.ims_team4.model.Offer;
import com.ims_team4.repository.OfferRepository;
import com.ims_team4.repository.impl.OfferRepositoryImpl;
import com.ims_team4.service.OfferService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// Duc Long
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepositoryImpl offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<OfferDTO> getAllOffer() {
        return offerRepository.getAllOffer().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDTO> getAllOfferByNameMailDepStatus(String text, int dep, int status) {
        return offerRepository.getAllOfferByNameMailDepStatus(text, dep, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OfferDTO convertToDTO(@NotNull Offer offer) {
        return OfferDTO.builder()
                .id(offer.getId())
                .position(offer.getPosition().getId())
                .interviewInfo(offer.getInterviewInfo())
                .contractPeriodFrom(offer.getContractPeriodFrom())
                .contractPeriodTo(offer.getContractPeriodTo())
                .interviewNotes(offer.getInterviewNotes())
                .statusOffer(offer.getStatusOffer())
                .contractType(offer.getContractType().getId())
                .level(offer.getLevel().getId())
                .department(offer.getDepartment().getId())
                .recruiterOwner(offer.getRecruiterOwner())
                .dueDate(offer.getDueDate())
                .basicSalary(offer.getBasicSalary())
                .note(offer.getNote())
                .candidateId(offer.getCandidate().getId())
                .employeeId(offer.getEmployee().getId())
                .build();
    }
}

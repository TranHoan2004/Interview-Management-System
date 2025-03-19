package com.ims_team4.service.impl;

import com.ims_team4.dto.OfferDTO;
import com.ims_team4.model.Offer;
import com.ims_team4.model.Users;
import com.ims_team4.repository.OfferRepository;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
// Duc Long
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    // <editor-fold desc="Code bởi @Duc Long- getALlOffer">
    @Override
    public List<OfferDTO> getAllOffer() {
        return offerRepository.getAllOffer().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OfferDTO> getAllOfferByNameMailDepStatus(String text, int dep, int status, int eid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Offer> offers = offerRepository.getAllOfferByNameMailDepStatus(text, dep, status, eid, pageable);
        long totalOffers = offerRepository.countAllOfferByNameMailDepStatus(text, dep, status, eid);
        List<OfferDTO> dtos = offers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, totalOffers);
    }

    @Override
    public OfferDTO getOfferById(long id) {
        return convertToDTO(offerRepository.getOfferById(id));
    }

//    @Override
//    public List<OfferDTO> getAllOfferByRecruiter(int id, Pageable pageable) {
//        return offerRepository.getAllOfferByRecruiter(id, pageable).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public boolean editOffer(int offerid, int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy) {
        return offerRepository.editOffer(offerid, salary, from, to, duedate, interviewNote, interviewId, note, recruiterOwner, cid, coid, did, eid, lid, pid, updateBy);
    }

    @Override
    public boolean updateStatusOffer(int offerid, int status) {
        return offerRepository.updateStatusOffer(offerid, status);
    }

    @Override
    public Page<OfferDTO> findPaginatedByEmployee(int page, int size, int id) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Offer> offers = offerRepository.getAllOfferByEmployee(id, pageable);
        long totalOffers = offerRepository.countAllOffers(id);
        List<OfferDTO> dtos = offers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, totalOffers);
    }

    @Override
    public OfferDTO getMaxOfferOfCandidate(int cid) {
        return convertToDTO(offerRepository.getMaxOfferOfCandidate(cid));
    }

//    @Override
//    public List<OfferDTO> getAllOfferOfManager(int mid) {
//        return offerRepository.getAllOfferOfManager(mid).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<OfferDTO> getAllOfferOfAdmin(int aid) {
//        return offerRepository.getAllOfferOfAdmin(aid).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public boolean createOffer(int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy) {
        return offerRepository.createOffer(salary, from, to, duedate, interviewNote, interviewId, note, recruiterOwner, cid, coid, did, eid, lid, pid, updateBy);
    }

    @Override
    public List<OfferDTO> getAllOfferFromToOfEid(LocalDate from, LocalDate to, int eid) {
        return offerRepository.getAllOfferFromToOfEid(from, to, eid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // </editor-fold>

    // <editor-fold desc="Code bởi @Duc Long- convertOfferToDTO">
    private OfferDTO convertToDTO(Offer offer) {

        Users candidate = userRepository.getUserById(offer.getCandidate().getId());
        String candidateFullName = (candidate != null) ? candidate.getFullname() : null;
        Users employee = userRepository.getUserById(offer.getEmployee().getId());
        String employeeFullName = (employee != null) ? employee.getFullname() : null;
        Users recruiter = userRepository.getUserById(offer.getRecruiterOwner());
        String recruiterFullName = (recruiter != null) ? recruiter.getFullname() : null;

        return OfferDTO.builder()
                .id(offer.getId())
                .position(offer.getPosition().getId())
                .contractPeriodFrom(offer.getContractPeriodFrom())
                .contractPeriodTo(offer.getContractPeriodTo())
                .interviewNotes(offer.getInterviewNotes())
                .statusOfferId(offer.getStatusOffer().getId())
                .statusOfferName(offer.getStatusOffer().getStatusName())
                .contractTypeId(offer.getContractType().getId())
                .contractTypeName(offer.getContractType().getName())
                .levelId(offer.getLevel().getId())
                .levelName(offer.getLevel().getName())
                .departmentId(offer.getDepartment().getId())
                .departmentName(offer.getDepartment().getName())
                .recruiterOwner(offer.getRecruiterOwner())
                .dueDate(offer.getDueDate())
                .basicSalary(offer.getBasicSalary())
                .note(offer.getNote())
                .candidateId(offer.getCandidate().getId())
                .employeeId(offer.getEmployee().getId())
                .interviewId(offer.getInterview().getId())
                .interviewTitle(offer.getInterview().getTitle())
                .createAt(offer.getCreatedAt())
                .updateBy(offer.getUpdatedBy())
                .positionName(offer.getPosition().getName())
                .candidateName(candidateFullName)
                .employeeName(employeeFullName)
                .recruiterName(recruiterFullName)
                .build();
    }
    // </editor-fold>
}

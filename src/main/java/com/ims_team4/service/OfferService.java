package com.ims_team4.service;

import com.ims_team4.dto.OfferDTO;
import com.ims_team4.model.utils.CandidateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

// Duc Long
public interface OfferService {
    // <editor-fold desc="Code bởi @Duc Long- getALlOffer">
    List<OfferDTO> getAllOffer();

    Page<OfferDTO> getAllOfferByNameMailDepStatus(String text, int dep, int status, int eid, int page, int size);

    // </editor-fold>
    OfferDTO getOfferById(long id);

    //List<OfferDTO> getAllOfferByRecruiter(int id, Pageable pageable);

    boolean editOffer(int offerid, int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy);

    boolean updateStatusOffer(int offerid, int status, CandidateStatus candidateStatus);

    Page<OfferDTO> findPaginatedByEmployee(int page, int size, int id);

    OfferDTO getMaxOfferOfCandidate(int cid);

//    Page<OfferDTO> findPaginatedManager(int page, int size, int mid);
//
//    Page<OfferDTO> findPaginatedAdmin(int page, int size, int aid);

    boolean createOffer(int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy);

    List<OfferDTO> getAllOfferFromToOfEid(LocalDate from, LocalDate to, int eid);
}

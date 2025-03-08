package com.ims_team4.service;

import com.ims_team4.dto.OfferDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

// Duc Long
public interface OfferService {
    // <editor-fold desc="Code bởi @Duc Long- getALlOffer">
    List<OfferDTO> getAllOffer();

    List<OfferDTO> getAllOfferByNameMailDepStatus(String text, int dep, int status, int eid);

    // </editor-fold>
    OfferDTO getOfferById(long id);

    List<OfferDTO> getAllOfferByRecruiter(int id);

    boolean editOffer(int offerid, int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid);

    boolean updateStatusOffer(int offerid, int status);

    Page<OfferDTO> findPaginated(int page, int size);

    OfferDTO getOfferOfCandidate(int cid);

    List<OfferDTO> getAllOfferOfManager(int mid);

    List<OfferDTO> getAllOfferOfAdmin(int aid);

    boolean createOffer(int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid);

    List<OfferDTO> getAllOfferFromToOfEid(LocalDate from, LocalDate to, int eid);
}

package com.ims_team4.repository;

import com.ims_team4.model.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

// Duc Long
public interface OfferRepository extends CrudRepository<Offer, Long> {
    List<Offer> getAllOffer();

    List<Offer> getAllOfferByNameMailDepStatus(String text, int dep, int status, int eid);

    Offer getOfferById(long id);

    List<Offer> getAllOfferByRecruiter(int id);

    boolean editOffer(int offerid, int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid);

    boolean updateStatusOffer(int offerid, int status);

    List<Offer> findAllOffers(Pageable pageable);

    Offer getOfferOfCandidate(int cid);

    List<Offer> getAllOfferOfManager(int mid);

    List<Offer> getAllOfferOfAdmin(int aid);

    boolean createOffer(int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid);

    List<Offer> getAllOfferFromToOfEid(LocalDate from, LocalDate to, int eid);
}

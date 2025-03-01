package com.ims_team4.repository;

import com.ims_team4.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Duc Long
public interface OfferRepository extends CrudRepository<Offer, Long> {
    // <editor-fold desc="Code bởi @Duc Long- getALlOffer">
    List<Offer> getAllOffer();
    List<Offer> getAllOfferByNameMailDepStatus(String text, int dep, int status);
    Offer getOfferById(long id);
    // </editor-fold>
}

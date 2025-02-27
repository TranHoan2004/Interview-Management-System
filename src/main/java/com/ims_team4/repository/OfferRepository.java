package com.ims_team4.repository;

import com.ims_team4.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Duc Long
public interface OfferRepository extends CrudRepository<Offer, Long> {
    List<Offer> getAllOffer();

    List<Offer> getAllOfferByNameMailDepStatus(String text, int dep, int status);
}

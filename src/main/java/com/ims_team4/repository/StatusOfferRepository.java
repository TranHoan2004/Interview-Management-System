package com.ims_team4.repository;

import com.ims_team4.model.StatusOffer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusOfferRepository extends CrudRepository<StatusOffer, Long> {
    List<StatusOffer> getAllStatusOffer();
}

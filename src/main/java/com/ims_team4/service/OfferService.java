package com.ims_team4.service;

import com.ims_team4.dto.OfferDTO;

import java.util.List;

// Duc Long
public interface OfferService {
    // <editor-fold desc="Code bởi @Duc Long- getALlOffer">
    List<OfferDTO> getAllOffer();

    List<OfferDTO> getAllOfferByNameMailDepStatus(String text, int dep, int status);
    // </editor-fold>
    OfferDTO getOfferById(long id);
}

package com.ims_team4.repository.impl;

import com.ims_team4.model.StatusOffer;
import com.ims_team4.repository.StatusOfferRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class StatusOfferRepositoryImpl implements StatusOfferRepository {

    private final EntityManager em;

    public StatusOfferRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<StatusOffer> getAllStatusOffer() {
        Session session = em.unwrap(Session.class);
        List<StatusOffer> statusOffers = session.createQuery("select s from StatusOffer s", StatusOffer.class).getResultList();
        session.close();
        return statusOffers;
    }

    @NotNull
    @Override
    public <S extends StatusOffer> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends StatusOffer> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<StatusOffer> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<StatusOffer> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<StatusOffer> findAllById(@NotNull Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull Long aLong) {

    }

    @Override
    public void delete(@NotNull StatusOffer entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends StatusOffer> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

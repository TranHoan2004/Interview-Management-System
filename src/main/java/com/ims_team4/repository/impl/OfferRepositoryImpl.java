package com.ims_team4.repository.impl;

import com.ims_team4.model.Offer;
import com.ims_team4.repository.OfferRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
// Duc Long
public class OfferRepositoryImpl implements OfferRepository {
    private final EntityManager em;

    public OfferRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Offer> getAllOffer() {
        Session session = em.unwrap(Session.class);
        List<Offer> offers = session.createQuery("select o from Offer o", Offer.class).getResultList();
        session.close();
        return offers;
    }

    @Override
    public List<Offer> getAllOfferByNameMailDepStatus(String text, int dep, int status) {
        Session session = em.unwrap(Session.class);
        String hql = "SELECT o FROM Offer o "
                + "JOIN o.candidate u "
                + "JOIN o.department d "
                + "WHERE (:text IS NULL OR u.user.fullname LIKE :text OR u.user.fullname LIKE :text) "
                + "AND (:dep = 0 OR d.id = :dep) "
                + "AND (:status = 0 OR o.status = :status)";

        Query query = session.createQuery(hql, Offer.class);

        query.setParameter("text", (text != null && !text.isEmpty()) ? "%" + text + "%" : null);
        query.setParameter("dep", dep);
        query.setParameter("status", status);

        List<Offer> offers = query.getResultList();
        session.close();
        return offers;
    }

    @Override
    public Offer getOfferById(long id) {
        Session session = em.unwrap(Session.class);
        Offer offer = null;
        try {
            offer = session.createQuery("select o from Offer o where o.id = :id", Offer.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return offer;
    }


    // </editor-fold>
    @NotNull
    @Override
    public <S extends Offer> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Offer> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NotNull
    public Optional<Offer> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Deprecated
    @Override
    @NotNull
    public Iterable<Offer> findAll() {
        return null;
    }

    @Override
    @NotNull
    public Iterable<Offer> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Offer entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }


}

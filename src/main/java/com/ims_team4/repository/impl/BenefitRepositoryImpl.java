package com.ims_team4.repository.impl;

import com.ims_team4.model.Benefit;
import com.ims_team4.repository.BenefitRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class BenefitRepositoryImpl implements BenefitRepository {

    private final EntityManager em;

    public BenefitRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Benefit> getAllBenefit() {
        Session session = em.unwrap(Session.class);
        List<Benefit> benefits = session.createQuery("select b from Benefit b", Benefit.class).getResultList();
        session.close();
        return benefits;
    }

    @Override
    public List<Benefit> findByNameIn(Set<String> names) {
        if (names.isEmpty()) {
            return Collections.emptyList();
        }
        return em.createQuery("SELECT b FROM Benefit b WHERE b.name IN :names", Benefit.class)
                .setParameter("names", names)
                .getResultList();

    }

    @Override
    public Set<Benefit> findByIdIn(Set<Long> ids) {
        Session session = em.unwrap(Session.class);
        List<Benefit> benefits = session.createQuery(
                        "SELECT b FROM Benefit b WHERE b.id IN (:ids)", Benefit.class)
                .setParameter("ids", ids)
                .getResultList();
        return Set.copyOf(benefits);
    }

    @NotNull
    @Override
    public <S extends Benefit> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Benefit> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<Benefit> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public Iterable<Benefit> findAll() {
        return null;
    }

    @Override
    @NotNull
    public Iterable<Benefit> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Benefit entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Benefit> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

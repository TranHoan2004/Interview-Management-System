package com.ims_team4.repository.impl;

import com.ims_team4.model.Candidate;
import com.ims_team4.repository.CandidateRepository;
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
// Duc Long
public class CandidateRepositoryImpl implements CandidateRepository {

    private final EntityManager em;

    public CandidateRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Candidate> getCandidateById(Long id) {
        Session session = em.unwrap(Session.class);
        List<Candidate> candidates = session.createQuery("select c from Candidate c where c.id = :id", Candidate.class).getResultList();
        session.close();
        return candidates;
    }

    @Override
    public List<Candidate> getAllCandidate() {
        Session session = em.unwrap(Session.class);
        List<Candidate> candidates = session.createQuery("select c from Candidate c", Candidate.class).getResultList();
        session.close();
        return candidates;
    }

    @Override
    @NotNull
    public <S extends Candidate> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Candidate> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NotNull
    public Optional<Candidate> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public Iterable<Candidate> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Candidate> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Candidate entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Candidate> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

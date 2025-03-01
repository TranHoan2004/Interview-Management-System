package com.ims_team4.repository.impl;

import com.ims_team4.model.HighestEducation;
import com.ims_team4.repository.HighestLevelRepository;
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
public class HighestLevelRepositoryImpl implements HighestLevelRepository {

    private final EntityManager em;

    public HighestLevelRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<HighestEducation> getAllHighestLevel() {
        Session session = em.unwrap(Session.class);
        List<HighestEducation> highestLevels = session
                .createQuery("from HighestEducation", HighestEducation.class)
                .getResultList();
        session.close();
        return highestLevels;
    }

    @NotNull
    @Override
    public <S extends HighestEducation> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends HighestEducation> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<HighestEducation> findById(@NotNull Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Integer integer) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<HighestEducation> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<HighestEducation> findAllById(@NotNull Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull Integer integer) {

    }

    @Override
    public void delete(@NotNull HighestEducation entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends HighestEducation> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

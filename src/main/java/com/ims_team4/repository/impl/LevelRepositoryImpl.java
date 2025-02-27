package com.ims_team4.repository.impl;

import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
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
public class LevelRepositoryImpl implements LevelRepository {

    private final EntityManager em;

    public LevelRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Level> getAllLevel() {
        Session session = em.unwrap(Session.class);
        List<Level> levels = session.createQuery("select l from Level l", Level.class).getResultList();
        session.close();
        return levels;
    }

    @NotNull
    @Override
    public <S extends Level> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Level> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<Level> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<Level> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Level> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Level entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Level> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

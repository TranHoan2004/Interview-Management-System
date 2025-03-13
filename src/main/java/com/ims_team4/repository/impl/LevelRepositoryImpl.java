package com.ims_team4.repository.impl;

import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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

    @Override
    public List<Level> findByNameIn(Set<String> names) {
        if (names.isEmpty()) {
            return Collections.emptyList();
        }
        return em.createQuery("SELECT l FROM Level l WHERE l.name IN :names", Level.class)
                .setParameter("names", names)
                .getResultList();

    }

    @Override
    public Set<Level> findByIdIn(Set<Long> ids) {
        Session session = em.unwrap(Session.class);
        List<Level> levels = session.createQuery(
                        "SELECT l FROM Level l WHERE l.id IN (:ids)", Level.class)
                .setParameter("ids", ids)
                .getResultList();
        return Set.copyOf(levels);

    }

    @Override
    public void flush() {

    }

    @Override
    @NotNull
    public <S extends Level> S saveAndFlush(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Level> List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(@NotNull Iterable<Level> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(@NotNull Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    @NotNull
    @Deprecated
    public Level getOne(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    @Deprecated
    public Level getById(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    public Level getReferenceById(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Level> Optional<S> findOne(@NotNull Example<S> example) {
        return Optional.empty();
    }

    @Override
    @NotNull
    public <S extends Level> List<S> findAll(@NotNull Example<S> example) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Level> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Level> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Level> long count(@NotNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Level> boolean exists(@NotNull Example<S> example) {
        return false;
    }

    @Override
    @NotNull
    public <S extends Level, R> R findBy(@NotNull Example<S> example, @NotNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Level> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Level> List<S> saveAll(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    @NotNull
    public Optional<Level> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public List<Level> findAll() {
        return List.of();
    }

    @Override
    @NotNull
    public List<Level> findAllById(@NotNull Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    @NotNull
    public void deleteById(Long aLong) {

    }

    @Override
    @NotNull
    public void delete(Level entity) {

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

    @Override
    @NotNull
    public List<Level> findAll(@NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public Page<Level> findAll(@NotNull Pageable pageable) {
        return null;
    }
}

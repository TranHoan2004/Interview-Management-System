package com.ims_team4.repository.impl;

import com.ims_team4.model.Position;
import com.ims_team4.repository.PositionRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
// Duc Long
public class PositionRepositoryImpl implements PositionRepository {

    private final EntityManager em;

    public PositionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Position> getAllPosition() {
        Session session = em.unwrap(Session.class);
        List<Position> positions = session.createQuery("from Position", Position.class).getResultList();
        session.close();
        return positions;
    }

    @Override
    public Optional<Position> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @NotNull
    @Override
    public <S extends Position> S saveAndFlush(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Position> List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(@NotNull Iterable<Position> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(@NotNull Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @NotNull
    @Override
    @Deprecated
    public Position getOne(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    @Deprecated
    public Position getById(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    public Position getReferenceById(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Position> Optional<S> findOne(@NotNull Example<S> example) {
        return Optional.empty();
    }

    @NotNull
    @Override
    public <S extends Position> List<S> findAll(@NotNull Example<S> example) {
        return List.of();
    }

    @NotNull
    @Override
    public <S extends Position> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return List.of();
    }

    @NotNull
    @Override
    public <S extends Position> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Position> long count(@NotNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Position> boolean exists(@NotNull Example<S> example) {
        return false;
    }

    @NotNull
    @Override
    public <S extends Position, R> R findBy(@NotNull Example<S> example, @NotNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Position> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Position> List<S> saveAll(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @NotNull
    @Override
    public Optional<Position> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public List<Position> findAll() {
        return List.of();
    }

    @NotNull
    @Override
    public List<Position> findAllById(@NotNull Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull Long aLong) {

    }

    @Override
    public void delete(@NotNull Position entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Position> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @NotNull
    @Override
    public List<Position> findAll(@NotNull Sort sort) {
        return List.of();
    }

    @NotNull
    @Override
    public Page<Position> findAll(@NotNull Pageable pageable) {
        return null;
    }
}

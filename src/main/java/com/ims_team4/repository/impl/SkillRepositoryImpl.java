package com.ims_team4.repository.impl;

import com.ims_team4.model.Skill;
import com.ims_team4.repository.SkillRepository;
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
public class SkillRepositoryImpl implements SkillRepository {
    private final EntityManager em;

    public SkillRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Skill> getAllSkill() {
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT s FROM Skill s", Skill.class)
                .getResultList();

    }

    @Override
    public List<Skill> findByNameIn(Set<String> names) {
        if (names.isEmpty()) {
            return Collections.emptyList();
        }
        return em.createQuery("SELECT s FROM Skill s WHERE s.name IN :names", Skill.class)
                .setParameter("names", names)
                .getResultList();

    }

    @Override
    public Set<Skill> findByIdIn(Set<Long> ids) {
        Session session = em.unwrap(Session.class);
        List<Skill> skills = session.createQuery(
                        "SELECT s FROM Skill s WHERE s.id IN (:ids)", Skill.class)
                .setParameter("ids", ids)
                .getResultList();
        return Set.copyOf(skills);

    }

    @Override
    public void flush() {

    }

    @Override
    @NotNull
    public <S extends Skill> S saveAndFlush(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Skill> List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(@NotNull Iterable<Skill> entities) {

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
    public Skill getOne(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    @Deprecated
    public Skill getById(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    public Skill getReferenceById(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Skill> Optional<S> findOne(@NotNull Example<S> example) {
        return Optional.empty();
    }

    @Override
    @NotNull
    public <S extends Skill> List<S> findAll(@NotNull Example<S> example) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Skill> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Skill> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Skill> long count(@NotNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Skill> boolean exists(@NotNull Example<S> example) {
        return false;
    }

    @Override
    @NotNull
    public <S extends Skill, R> R findBy(@NotNull Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Skill> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Skill> List<S> saveAll(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    @NotNull
    public Optional<Skill> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public List<Skill> findAll() {
        return List.of();
    }

    @Override
    @NotNull
    public List<Skill> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Skill entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Skill> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    @NotNull
    public List<Skill> findAll(@NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public Page<Skill> findAll(@NotNull Pageable pageable) {
        return null;
    }
}

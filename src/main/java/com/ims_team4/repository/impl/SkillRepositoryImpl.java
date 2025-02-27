package com.ims_team4.repository.impl;

import com.ims_team4.model.Skill;
import com.ims_team4.repository.SkillRepository;
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
public class SkillRepositoryImpl implements SkillRepository {

    private final EntityManager em;

    public SkillRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Skill> getAllSkill() {
        Session session = em.unwrap(Session.class);
        List<Skill> skills = session.createQuery("select s from Skill s", Skill.class).getResultList();
        session.close();
        return skills;
    }

    @NotNull
    @Override
    public <S extends Skill> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Skill> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<Skill> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<Skill> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Skill> findAllById(@NotNull Iterable<Long> longs) {
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
}

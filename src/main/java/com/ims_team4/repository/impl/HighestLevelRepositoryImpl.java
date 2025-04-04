package com.ims_team4.repository.impl;

import com.ims_team4.model.HighestLevel;
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
    public List<HighestLevel> getAllHighestLevel() {
        Session session = em.unwrap(Session.class);
        List<HighestLevel> highestLevels = session.createQuery("select h from HighestLevel h", HighestLevel.class).getResultList();
        session.close();
        return highestLevels;
    }

    @NotNull
    @Override
    public <S extends HighestLevel> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends HighestLevel> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<HighestLevel> findById(@NotNull Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Integer integer) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<HighestLevel> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<HighestLevel> findAllById(@NotNull Iterable<Integer> integers) {
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
    public void delete(@NotNull HighestLevel entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends HighestLevel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<HighestLevel> findByIdCustom(@NotNull Long id) {
        return Optional.ofNullable(em.find(HighestLevel.class, id)); // ✅ Dùng EntityManager tìm trực tiếp
    }




}

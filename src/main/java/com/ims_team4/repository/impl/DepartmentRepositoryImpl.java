package com.ims_team4.repository.impl;

import com.ims_team4.model.Department;
import com.ims_team4.repository.DepartmentRepository;
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
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final EntityManager em;

    public DepartmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Department> getAllDepartment() {
        Session session = em.unwrap(Session.class);
        List<Department> departments = session.createQuery("select d from Department d", Department.class).getResultList();
        session.close();
        return departments;
    }

    @NotNull
    @Override
    public <S extends Department> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends Department> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<Department> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<Department> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Department> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Department entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Department> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

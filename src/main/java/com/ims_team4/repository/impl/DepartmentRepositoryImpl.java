package com.ims_team4.repository.impl;

import com.ims_team4.model.Department;
import com.ims_team4.repository.DepartmentRepository;
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
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final EntityManager em;

    public DepartmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Department> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Department> getAllDepartment() {
        Session session = em.unwrap(Session.class);
        List<Department> departments = session.createQuery("select d from Department d", Department.class).getResultList();
        session.close();
        return departments;
    }

    @Override
    public Department getDepartmentById(long id) {
            try {
                return em.createQuery("SELECT d FROM Department d WHERE d.id = :id", Department.class)
                        .setParameter("id", id)
                        .getSingleResult();
            } catch (Exception e) {
                return null; // hoặc throw new RuntimeException("Department not found");
            }

    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends Department> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Department> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Department> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    @Deprecated
    public Department getOne(Long aLong) {
        return null;
    }

    @Override
    @Deprecated
    public Department getById(Long aLong) {
        return null;
    }

    @Override
    public Department getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Department> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Department> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Department> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Department> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Department> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Department> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Department, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Department> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Department> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Department> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }

    @Override
    public List<Department> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Department entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Department> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Department> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return null;
    }
}

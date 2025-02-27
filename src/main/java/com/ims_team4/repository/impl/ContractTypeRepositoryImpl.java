package com.ims_team4.repository.impl;

import com.ims_team4.model.ContractType;
import com.ims_team4.repository.ContractTypeRepository;
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
public class ContractTypeRepositoryImpl implements ContractTypeRepository {

    private final EntityManager em;

    public ContractTypeRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ContractType> getAllContractType() {
        Session session = em.unwrap(Session.class);
        List<ContractType> s = session.createQuery("select c from ContractType c", ContractType.class).getResultList();
        session.close();
        return s;
    }

    @NotNull
    @Override
    public <S extends ContractType> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends ContractType> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<ContractType> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<ContractType> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<ContractType> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull ContractType entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends ContractType> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

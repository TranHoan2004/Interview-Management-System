package com.ims_team4.repository.impl;

import com.ims_team4.model.Offer;
import com.ims_team4.model.User;
import com.ims_team4.repository.UserRepository;
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
// Duc Long
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = em.unwrap(Session.class);
        return session
                .createQuery("from User u where u.email=:email", User.class)
                .setParameter("email", email)
                .uniqueResultOptional();
    }

    @Override
    public List<User> getAllUser() {
        Session session = em.unwrap(Session.class);
        List<User> users = session
                .createQuery("from User", User.class)
                .getResultList();
        session.close();
        return users;
    }

    @Override
    public User getUserById(long id) {
        Session session = em.unwrap(Session.class);
        User user = session.createQuery("select u from com.ims_team4.model.User u where u.id = :id", User.class).setParameter("id", id).getSingleResult();
        session.close();
        return user;
    }

    @NotNull
    @Override
    public <S extends User> S save(@NotNull S entity) {
        return null;
    }

    @NotNull
    @Override
    public <S extends User> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<User> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<User> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull User entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

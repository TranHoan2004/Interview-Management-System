package com.ims_team4.repository.impl;

import com.ims_team4.model.Users;
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
    public List<String> getEmails() {
        return em
                .unwrap(Session.class)
                .createQuery("select u.email from Users u", String.class)
                .getResultList();
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        Session session = em.unwrap(Session.class);
        return session
                .createQuery("from Users u where u.email=:email", Users.class)
                .setParameter("email", email)
                .uniqueResultOptional();
    }

    @Override
    public List<Users> getAllUser() {
        Session session = em.unwrap(Session.class);
        List<Users> users = session
                .createQuery("from Users", Users.class)
                .getResultList();
        session.close();
        return users;
    }

    @Override
    public Users getUserById(long id) {
        Session session = em.unwrap(Session.class);
        Users user = session.createQuery("select u from com.ims_team4.model.Users u where u.id = :id", Users.class).setParameter("id", id).getSingleResult();
        session.close();
        return user;
    }

    @NotNull
    @Override
    public <S extends Users> S save(@NotNull S entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    @NotNull
    @Override
    public <S extends Users> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @NotNull
    @Override
    public Optional<Users> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @NotNull
    @Override
    public Iterable<Users> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Users> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Users entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Users> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean checkExistsById(@NotNull Long userId) {
        Session session = em.unwrap(Session.class);
        Long count = session.createQuery("select count(u) from Users u where u.id = :id", Long.class)
                .setParameter("id", userId)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public void removeById(@NotNull Long userId) {
        Session session = em.unwrap(Session.class);
        Users user = session.get(Users.class, userId);
        if (user != null) {
            session.remove(user);
//            System.out.println("🗑️ User with ID " + userId + " deleted!");
        }
//        else {
//            System.out.println("⚠️ User with ID " + userId + " not found!");
//        }
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = em.createQuery("SELECT COUNT(u) FROM Users u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        Long count = em.createQuery("SELECT COUNT(u) FROM Users u WHERE u.phone = :phone", Long.class)
                .setParameter("phone", phone)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmailAndUserIdNot(String email, Long userId) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM Users u WHERE u.email = :email AND u.id <> :userId", Long.class)
                .setParameter("email", email)
                .setParameter("userId", userId)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByPhoneAndUserIdNot(String phone, Long userId) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM Users u WHERE u.phone = :phone AND u.id <> :userId", Long.class)
                .setParameter("phone", phone)
                .setParameter("userId", userId)
                .getSingleResult();
        return count != null && count > 0;
    }

}

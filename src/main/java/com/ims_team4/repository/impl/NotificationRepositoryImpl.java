package com.ims_team4.repository.impl;

import com.ims_team4.model.Notification;
import com.ims_team4.repository.NotificationRepository;
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
public class NotificationRepositoryImpl implements NotificationRepository {
    private final EntityManager em;

    public NotificationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Notification> getAllNotificationByUserId() {
        Session session = em.unwrap(Session.class);
        List<Notification> notifications = session.createQuery("select n from Notification n", Notification.class).getResultList();
        session.close();
        return notifications;
    }

    @NotNull
    @Override
    public <S extends Notification> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Notification> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NotNull
    public Optional<Notification> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public Iterable<Notification> findAll() {
        return null;
    }

    @Override
    @NotNull
    public Iterable<Notification> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Notification entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Notification> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

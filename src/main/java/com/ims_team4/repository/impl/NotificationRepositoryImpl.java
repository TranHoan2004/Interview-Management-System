//package com.ims_team4.repository.impl;
//
//import com.ims_team4.model.Notification;
//import com.ims_team4.model.Users;
//import com.ims_team4.repository.NotificationRepository;
//import jakarta.persistence.EntityManager;
//import org.hibernate.Session;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//@Repository
//@Transactional(propagation = Propagation.REQUIRED)
//// HoanTX
//public class NotificationRepositoryImpl implements NotificationRepository {
//    private final EntityManager em;
//    private final Logger logger = Logger.getLogger(NotificationRepositoryImpl.class.getName());
//
//    public NotificationRepositoryImpl(EntityManager em) {
//        this.em = em;
//    }
//
//    @Override
//    public List<Notification> getAllNotificationByUserId(Long id, int start, int maxResult) {
//        Session session = em.unwrap(Session.class);
//        List<Notification> notifications = session.createQuery("FROM Notification n WHERE n.user.id=:id", Notification.class)
//                .setParameter("id", id)
//                .setFirstResult(start)
//                .setMaxResults(maxResult)
//                .getResultList();
//        session.close();
//        return notifications;
//    }
//
//    @NotNull
//    @Override
//    public <S extends Notification> S save(@NotNull S entity) {
//        Session session = em.unwrap(Session.class);
//        Users user = session
//                .createQuery("FROM Users u WHERE u.id = :userId", Users.class)
//                .setParameter("userId", entity.getId())
//                .getSingleResult();
//        entity.setUser(user);
//        return session.merge(entity);
//    }
//
//    @Override
//    @NotNull
//    public <S extends Notification> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    @NotNull
//    public Optional<Notification> findById(@NotNull Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(@NotNull Long aLong) {
//        return false;
//    }
//
//    @Override
//    @NotNull
//    public Iterable<Notification> findAll() {
//        return null;
//    }
//
//    @Override
//    @NotNull
//    public Iterable<Notification> findAllById(@NotNull Iterable<Long> longs) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(@NotNull Long aLong) {
//
//    }
//
//    @Override
//    public void delete(@NotNull Notification entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(@NotNull Iterable<? extends Notification> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//}

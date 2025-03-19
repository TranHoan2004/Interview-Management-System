package com.ims_team4.repository.impl;

import com.ims_team4.model.Chat;
import com.ims_team4.model.ChatDetail;
import com.ims_team4.repository.ChatDetailRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatDetailRepositoryImpl implements ChatDetailRepository {

    private EntityManager em;

    public ChatDetailRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ChatDetail> getAllChatDetail() {
        Session session = em.unwrap(Session.class);
        List<ChatDetail> chatDetails = session.createQuery("select c from ChatDetail c", ChatDetail.class).getResultList();
        session.close();
        return chatDetails;
    }

    @Override
    public List<ChatDetail> getLastestChatDetailOfRecruiterAndManager() {
        Session session = em.unwrap(Session.class);

        List<ChatDetail> chatDetails = session.createQuery(
                "SELECT cd FROM ChatDetail cd " +
                        "WHERE cd.id IN (" +
                        "    SELECT MAX(cd2.id) " +
                        "    FROM ChatDetail cd2 " +
                        "    GROUP BY cd2.chat.id" +
                        ")",
                ChatDetail.class
        ).getResultList();

        return chatDetails;
    }



    @Override
    public List<ChatDetail> getFirstChatDetailOfRecruiter(int rid) {
        Session session = em.unwrap(Session.class);

        List<ChatDetail> chatDetails = session.createQuery(
                        "SELECT cd FROM Chat c " +
                                "JOIN c.chatDetails cd " +
                                "WHERE c.recruiter.id = :rid " +
                                "ORDER BY c.id DESC", ChatDetail.class)
                .setParameter("rid", rid)
                .setMaxResults(1)
                .getResultList();

        return chatDetails;
    }

    @Override
    public List<ChatDetail> getFirstChatDetailOfManager(int mid) {
        Session session = em.unwrap(Session.class);

        List<ChatDetail> chatDetails = session.createQuery(
                        "SELECT cd FROM Chat c " +
                                "JOIN c.chatDetails cd " +
                                "WHERE c.manager.id = :mid " +
                                "ORDER BY c.id DESC", ChatDetail.class)
                .setParameter("mid", mid)
                .setMaxResults(1)
                .getResultList();

        return chatDetails;
    }

    @Override
    public List<ChatDetail> getAllChatDetailOfRecruiter(int rid) {
        Session session = em.unwrap(Session.class);
        List<ChatDetail> chatDetails = session.createQuery("SELECT cd FROM Chat c " +
                "JOIN c.chatDetails cd " +
                "WHERE c.recruiter.id = :rid", ChatDetail.class)
                .setParameter("rid", rid)
                .getResultList();
        session.close();
        return chatDetails;
    }

    @Override
    public List<ChatDetail> getAllChatDetailsByChatId(int chatid) {
        Session session = em.unwrap(Session.class);
        List<ChatDetail> chatDetails = session.createQuery("select c from ChatDetail c where c.chat.id = :chatid", ChatDetail.class)
                .setParameter("chatid", chatid)
                .getResultList();
        session.close();
        return chatDetails;
    }

    @Override
    @Transactional
    public void saveChatDetail(ChatDetail chatDetail) {
        try {
            em.persist(chatDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertChat(int cid, String msg) {

    }

    @Override
    public <S extends ChatDetail> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ChatDetail> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ChatDetail> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<ChatDetail> findAll() {
        return null;
    }

    @Override
    public Iterable<ChatDetail> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ChatDetail entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ChatDetail> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

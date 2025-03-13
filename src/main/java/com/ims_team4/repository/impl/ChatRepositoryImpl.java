package com.ims_team4.repository.impl;

import com.ims_team4.model.Chat;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Offer;
import com.ims_team4.repository.ChatRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ChatRepositoryImpl implements ChatRepository {
    private final EntityManager em;

    public ChatRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Chat> getAllChat() {
        Session session = em.unwrap(Session.class);
        List<Chat> chats = session.createQuery("select c from Chat c", Chat.class).getResultList();
        session.close();
        return chats;
    }

    @Override
    public List<Chat> getAllChatOfRecruiter(int recruiterId) {
        Session session = em.unwrap(Session.class);
        List<Chat> chats = session.createQuery("select c from Chat c where c.recruiter.id =:recruiterId", Chat.class)
                .setParameter("recruiterId", recruiterId)
                .getResultList();
        session.close();
        return chats;
    }

    @Override
    public List<Chat> getAllChatOfManager(int managerId) {
        Session session = em.unwrap(Session.class);
        List<Chat> chats = session.createQuery("select c from Chat c where c.manager.id =: managerId", Chat.class)
                .setParameter("recruiterId", managerId)
                .getResultList();
        session.close();
        return chats;
    }

    @Transactional
    @Override
    public int insertChat(int rid, int mid) {
        int chatId = -1; // Giá trị mặc định nếu thất bại
        Session session = em.unwrap(Session.class);
        Employee recruiter = session.get(Employee.class, rid);
        Employee manager = session.get(Employee.class, mid);
        try {
            Chat chat = new Chat();
            chat.setManager(manager);
            chat.setRecruiter(recruiter);

            em.persist(chat);
            em.flush(); // Đẩy dữ liệu xuống DB để có thể lấy ID ngay lập tức

            chatId = (int) chat.getId(); // Lấy ChatID sau khi persist

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return chatId;
    }

    @Override
    public Chat getChatById(int id) {
        Session session = em.unwrap(Session.class);
        Chat chat = session.createQuery("select c from Chat c where c.id =: id", Chat.class)
                .setParameter("id", id)
                .getSingleResult();
        session.close();
        return chat;
    }

    @Override
    public int getChatIdByRecruiterAndManager(int rid, int mid) {
        Session session = em.unwrap(Session.class);
        Chat chat = session.createQuery("select c from Chat c where c.manager.id =: mid and c.recruiter.id =: rid", Chat.class)
                .setParameter("rid", rid)
                .setParameter("mid", mid)
                .getSingleResult();
        session.close();
        return (int) chat.getId();
    }

    @Override
    public <S extends Chat> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Chat> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Chat> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Chat> findAll() {
        return null;
    }

    @Override
    public Iterable<Chat> findAllById(Iterable<Long> longs) {
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
    public void delete(Chat entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Chat> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

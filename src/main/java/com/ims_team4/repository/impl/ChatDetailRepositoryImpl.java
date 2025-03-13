package com.ims_team4.repository.impl;

import com.ims_team4.model.Chat;
import com.ims_team4.model.ChatDetail;
import com.ims_team4.repository.ChatDetailRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

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
        return List.of();
    }

    @Override
    public List<ChatDetail> getFirstChatDetailOfRecruiter(int rid) {
        return List.of();
    }

    @Override
    public List<ChatDetail> getFirstChatDetailOfManager(int mid) {
        return List.of();
    }

    @Override
    public List<ChatDetail> getAllChatDetailOfRecruiter(int rid) {
        return List.of();
    }

    @Override
    public List<ChatDetail> getAllChatDetailsByChatId(int chatid) {
        return List.of();
    }

    @Override
    public void saveChatDetail(ChatDetail chatDetail) {

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

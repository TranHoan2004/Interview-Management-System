package com.ims_team4.repository.impl;

import com.ims_team4.model.Interview;
import com.ims_team4.model.Offer;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.InterviewRepository;
import com.ims_team4.repository.InterviewRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
// Tran Dang Vu
public class InterviewRepositoryImpl implements InterviewRepository {

    private final EntityManager entityManager;

    public InterviewRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Interview> findUpcomingInterviews(LocalDateTime from, LocalDateTime to) {
        String jpql = "SELECT i FROM Interview i "
                + "WHERE i.scheduleTime BETWEEN :from AND :to "
                + "AND i.status <> :cancelled";

        TypedQuery<Interview> query = entityManager.createQuery(jpql, Interview.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.setParameter("cancelled", InterviewStatus.CANCELLED);

        return query.getResultList();
    }

    @Override
    public List<Interview> searchInterviews(String keyword,
                                            String interviewerName,
                                            String status) {
        StringBuilder sb = new StringBuilder(
                "SELECT DISTINCT i FROM Interview i "
                        + "LEFT JOIN i.employees e "
                        + "LEFT JOIN i.candidates c "
                        + "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();
        int paramIndex = 1;

        if (keyword != null && !keyword.isEmpty()) {
            sb.append(" AND (i.title LIKE ?" + paramIndex + " "
                    + "OR i.feedback LIKE ?" + paramIndex + " "
                    + "OR i.locations LIKE ?" + paramIndex + ") ");
            params.add("%" + keyword + "%");
            paramIndex++;
        }

        if (interviewerName != null && !interviewerName.isEmpty()) {
            // Tuỳ thuộc vào entity Employee (ví dụ có trường fullName)
            sb.append(" AND (e.fullName LIKE ?" + paramIndex + ") ");
            params.add("%" + interviewerName + "%");
            paramIndex++;
        }

        if (status != null && !status.isEmpty()) {
            sb.append(" AND i.status = ?" + paramIndex + " ");
            params.add(InterviewStatus.valueOf(status.toUpperCase()));
            paramIndex++;
        }

        TypedQuery<Interview> query = entityManager.createQuery(sb.toString(), Interview.class);

        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }

        return query.getResultList();
    }

    @Override
    public List<Interview> findByStatus(InterviewStatus status) {
        return List.of();
    }

    @Override
    public List<Interview> findByScheduleTimeBetween(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }

    @Override
    public List<Interview> getAllInterview() {
        Session session = entityManager.unwrap(Session.class);
        List<Interview> interviews = session.createQuery("select i from Interview i", Interview.class).getResultList();
        session.close();
        return interviews;
    }

    @Override
    public <S extends Interview> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Interview> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Interview> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Interview> findAll() {
        return null;
    }

    @Override
    public Iterable<Interview> findAllById(Iterable<Long> longs) {
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
    public void delete(Interview entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Interview> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
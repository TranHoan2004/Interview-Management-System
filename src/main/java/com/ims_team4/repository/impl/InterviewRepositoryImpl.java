package com.ims_team4.repository.impl;

import com.ims_team4.model.Interview;
import com.ims_team4.repository.InterviewRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public Interview save(Interview interview) {
        if (interview.getId() == null) {
            entityManager.persist(interview);
            return interview;
        } else {
            return entityManager.merge(interview);
        }
    }

    @Override
    public Optional<Interview> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Interview.class, id));
    }

    @Override
    public List<Interview> findAll() {
        return entityManager.createQuery("SELECT i FROM Interview i", Interview.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public List<Interview> findByInterviewerId(Long interviewerId) {
        return entityManager.createQuery(
                        "SELECT i FROM Interview i WHERE i.interviewerId = :interviewerId", Interview.class)
                .setParameter("interviewerId", interviewerId)
                .getResultList();
    }


}
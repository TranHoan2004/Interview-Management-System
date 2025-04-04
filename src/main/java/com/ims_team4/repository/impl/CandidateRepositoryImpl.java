package com.ims_team4.repository.impl;

import com.ims_team4.model.Candidate;
import com.ims_team4.model.Employee;
import com.ims_team4.model.HighestLevel;
import com.ims_team4.model.Position;
import com.ims_team4.model.utils.CandidateStatus;
import com.ims_team4.repository.CandidateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
public class CandidateRepositoryImpl implements CandidateRepository {

    private final EntityManager em;

    public CandidateRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Candidate> getCandidateById(Long id) {
        Session session = em.unwrap(Session.class);
        List<Candidate> candidates = session.createQuery("select c from Candidate c where c.id = :id", Candidate.class).getResultList();
        session.close();
        return candidates;
    }

    @Override
    public List<Candidate> getAllCandidate() {
        Session session = em.unwrap(Session.class);
        List<Candidate> candidates = session.createQuery("select c from Candidate c", Candidate.class).getResultList();
        session.close();
        return candidates;
    }

    @Override
    public List<Candidate> getAllCandidateNotBan() {
        Session session = em.unwrap(Session.class);
        List<Candidate> candidates = session.createQuery("select c from Candidate c where c.status <> :role", Candidate.class)
                .setParameter("role", CandidateStatus.BANNED)
                .getResultList();
        session.close();
        return candidates;
    }

    @Override
    @Transactional
    public <S extends Candidate> S save(@NotNull S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Candidate entity must not be null");
        }

        try {
            S savedEntity = em.merge(entity); // Đảm bảo merge() không trả về null
            em.flush(); // Đẩy dữ liệu vào DB ngay lập tức
            return savedEntity;
        } catch (Exception e) {
            System.err.println("❌ Error saving candidate: " + e.getMessage());
            throw new RuntimeException("Failed to save candidate", e);
        }
    }


    @Override
    @NotNull
    public <S extends Candidate> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NotNull
    public Optional<Candidate> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    @NotNull
    public Iterable<Candidate> findAll() {
        return null;
    }

    @NotNull
    @Override
    public Iterable<Candidate> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(@NotNull Candidate entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Candidate> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Candidate> getAllCandidate2() {
        Session session = em.unwrap(Session.class);
        return session.createQuery(
                        "SELECT c FROM Candidate c " +
                                "INNER JOIN FETCH c.position p " +
                                "INNER JOIN FETCH c.employee e " +
                                "INNER JOIN FETCH c.skills s", Candidate.class)
                .getResultList();
    }


    @Override
    public Optional<Candidate> getCandidateByUserId(Long userId) {
        return em.unwrap(Session.class)
                .createQuery("SELECT c FROM Candidate c LEFT JOIN FETCH c.user u WHERE u.id = :userId", Candidate.class)
                .setParameter("userId", userId)
                .uniqueResultOptional();
    }



    @Override
    public Optional<Candidate> findByUserId(Long userId) {
        return em.unwrap(Session.class)
                .createQuery("SELECT c FROM Candidate c LEFT JOIN FETCH c.user u WHERE c.user.id = :userId", Candidate.class)
                .setParameter("userId", userId)
                .uniqueResultOptional();
    }



    @Override
    @Transactional
    public void deleteByUserId(@NotNull Long userId) {
//        System.out.println("🔴 Deleting candidate with userId: " + userId);

        int deletedCount = em.createQuery("DELETE FROM Candidate c WHERE c.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();

//        if (deletedCount > 0) {
//            System.out.println("✅ Candidate deleted from database!");
//        } else {
//            System.out.println("❌ No candidate found with userId: " + userId);
//        }
    }

    @Override
    @Transactional
    public void banCandidateByUserId(Long userId) {
        int updatedCount = em.unwrap(Session.class)
                .createQuery("UPDATE Candidate c SET c.status = :status WHERE c.user.id = :userId")
                .setParameter("status", CandidateStatus.BANNED) // Dùng Enum thay vì String
                .setParameter("userId", userId)
                .executeUpdate();

//        if (updatedCount > 0) {
//            System.out.println("✅ Candidate banned successfully!");
//        } else {
//            System.out.println("❌ No candidate found with userId: " + userId);
//        }
    }


    @Override
    public Optional<HighestLevel> findHighestLevelById(long id) {
        return Optional.ofNullable(
                em.unwrap(Session.class).createQuery("SELECT h FROM HighestLevel h WHERE h.id = :id", HighestLevel.class)
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }

    @Override
    public Optional<Position> findPositionById(long id) {
        return Optional.ofNullable(
                em.unwrap(Session.class).createQuery("SELECT p FROM Position p WHERE p.id = :id", Position.class)
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }

    @Override
    public Optional<Employee> findEmployeeById(long id) {
        return Optional.ofNullable(
                em.unwrap(Session.class).createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class)
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }


    @Override
    public Optional<Employee> findDefaultEmployee() {
        return Optional.ofNullable(
                em.unwrap(Session.class)
                        .createQuery("SELECT e FROM Employee e WHERE e.workingName = 'Default Employee'", Employee.class)
                        .uniqueResult()
        );
    }


    @Override
    public int countByStatus(CandidateStatus status) {
        Query query = em.createQuery("SELECT COUNT(c) FROM Candidate c WHERE c.status = :status");
        query.setParameter("status", status);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public int countAllCandidates() {
        Query query = em.createQuery("SELECT COUNT(c) FROM Candidate c");
        return ((Long) query.getSingleResult()).intValue();
    }


}



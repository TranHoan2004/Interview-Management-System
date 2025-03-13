package com.ims_team4.repository.impl;

import com.ims_team4.model.Job;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.JobRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JobRepositoryImpl implements JobRepository {

    @Autowired
    private EntityManager em;

    @Override
    public List<Job> findAllWithDetails() {
        return List.of();
    }

    @Override
    public List<Job> findByTitleContaining(String title) {
        return List.of();
    }

    @Override
    public List<Job> findByStatus(Boolean status) {
        return List.of();
    }

    @Override
    public List<Job> findByTitleContainingAndStatus(String title, Boolean status) {
        return List.of();
    }

    @Override
    public Page<Job> findJobsForManager(Long userId, String title, Boolean status, HrRole role, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Job> findJobDetailForManager(Long managerId, Long jobId) {
        String sql = "SELECT " +
                     "    j.*, " +
                     "    e.id AS manager_id, " +
                     "    GROUP_CONCAT(DISTINCT l.name SEPARATOR ', ') AS level_names, " +
                     "    GROUP_CONCAT(DISTINCT s.name SEPARATOR ', ') AS skill_names " +
                     "FROM Job j " +
                     "JOIN employee e ON e.position_id = j.id " +
                     "JOIN users u ON e.id = u.id " +
                     "JOIN job_level jl ON jl.job_id = j.id " +
                     "JOIN levels l ON jl.level_id = l.id " +
                     "JOIN job_skill js ON js.job_id = j.id " +
                     "JOIN skill s ON js.skill_id = s.id " +
                     "WHERE u.id = :managerId " +
                     "AND e.role = 'ROLE_MANAGER' " +
                     "AND j.id = :jobId " +
                     "GROUP BY j.id, e.id";

        try {
            Object result = em.createNativeQuery(sql, Job.class)
                    .setParameter("managerId", managerId)
                    .setParameter("jobId", jobId)
                    .getSingleResult();
            return Optional.of((Job) result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Page<Job> findJobs(String title, Boolean status, Pageable pageable) {
        return null;
    }

//    @Override
//    public Job saveJob(Job job) {
//        if (job.getId() == null) {
//            em.persist(job);
//        } else {
//            job = em.merge(job);
//        }
//        em.flush();
//        return job;
//    }

    @Override
    public Job saveJob(Job job) {
        if (job.getId() != null) {
            throw new IllegalArgumentException("Job ID must be null when creating a new job.");
        }
        em.persist(job); // Chỉ dùng persist để đảm bảo luôn INSERT
        em.flush(); // Đẩy dữ liệu ngay lập tức vào database
        return job;
    }

    @Override
    public Optional<Job> findJobById(Long id) {
        try (Session session = em.unwrap(Session.class)) {
            Job job = session.createQuery(
                            "SELECT j FROM Job j " +
                                    "LEFT JOIN FETCH j.skills " +
                                    "LEFT JOIN FETCH j.levels " +
                                    "LEFT JOIN FETCH j.benefits " +
                                    "WHERE j.id = :jobId", Job.class)
                    .setParameter("jobId", id)
                    .uniqueResult();

            return Optional.ofNullable(job);
        }
    }

    @Override
    @NotNull
    public Optional<Job> findById(@NotNull Long id) {
        try (Session session = em.unwrap(Session.class)) {
            Job job = session.createQuery(
                            "SELECT j FROM Job j " +
                            "LEFT JOIN FETCH j.skills " +
                            "LEFT JOIN FETCH j.levels " +
                            "LEFT JOIN FETCH j.benefits " +
                            "WHERE j.id = :jobId", Job.class)
                    .setParameter("jobId", id)
                    .uniqueResult();

            return Optional.ofNullable(job);
        }

    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    @NotNull
    public <S extends Job> S saveAndFlush(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Job> List<S> saveAllAndFlush(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(@NotNull Iterable<Job> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(@NotNull Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @NotNull
    @Override
    @Deprecated
    public Job getOne(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    @Deprecated
    public Job getById(@NotNull Long aLong) {
        return null;
    }

    @NotNull
    @Override
    public Job getReferenceById(@NotNull Long aLong) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Job> Optional<S> findOne(@NotNull Example<S> example) {
        return Optional.empty();
    }

    @Override
    @NotNull
    public <S extends Job> List<S> findAll(@NotNull Example<S> example) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Job> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public <S extends Job> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Job> long count(@NotNull Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Job> boolean exists(@NotNull Example<S> example) {
        return false;
    }

    @Override
    @NotNull
    public <S extends Job, R> R findBy(@NotNull Example<S> example, @NotNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Job> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Job> List<S> saveAll(@NotNull Iterable<S> entities) {
        return List.of();
    }

    @Override
    @NotNull
    public List<Job> findAll() {
        return List.of();
    }

    @Override
    @NotNull
    public List<Job> findAllById(@NotNull Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull Long aLong) {

    }

    @Override
    public void delete(@NotNull Job entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends Job> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    @NotNull
    public List<Job> findAll(@NotNull Sort sort) {
        return List.of();
    }

    @Override
    @NotNull
    public Page<Job> findAll(@NotNull Pageable pageable) {
        return null;
    }
}

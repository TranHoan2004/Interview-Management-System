package com.ims_team4.repository.impl;

import com.ims_team4.model.*;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.repository.OfferRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
// Duc Long
public class OfferRepositoryImpl implements OfferRepository {
    private final EntityManager em;

    public OfferRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Offer> getAllOffer() {
        Session session = em.unwrap(Session.class);
        List<Offer> offers = session.createQuery("select o from Offer o", Offer.class).getResultList();
        session.close();
        return offers;
    }

    @Override
    public List<Offer> getAllOfferByNameMailDepStatus(String text, int dep, int status, int eid, Pageable pageable) {
        Session session = em.unwrap(Session.class);
        Employee e = session.get(Employee.class, eid);
        String hql = "";
        if (e.getRole() == HrRole.ROLE_RECRUITER) {
            hql = "SELECT o FROM Offer o "
                    + "JOIN o.candidate u "
                    + "JOIN o.department d "
                    + "JOIN o.statusOffer s "
                    + "WHERE (:text IS NULL OR u.user.fullname LIKE :text OR u.user.fullname LIKE :text) "
                    + "AND (:dep = 0 OR d.id = :dep) "
                    + "AND (:status = 0 OR s.id = :status)"
                    + "AND (o.recruiterOwner = :eid)";
        }
        if (e.getRole() == HrRole.ROLE_MANAGER || e.getRole() == HrRole.ROLE_ADMINISTRATOR) {
            hql = "SELECT o FROM Offer o "
                    + "JOIN o.candidate u "
                    + "JOIN o.department d "
                    + "JOIN o.statusOffer s "
                    + "WHERE (:text IS NULL OR u.user.fullname LIKE :text OR u.user.fullname LIKE :text) "
                    + "AND (:dep = 0 OR d.id = :dep) "
                    + "AND (:status = 0 OR s.id = :status)"
                    + "AND (o.employee.id = :eid)";
        }
        Query query = session.createQuery(hql, Offer.class);

        query.setParameter("text", (text != null && !text.isEmpty()) ? "%" + text + "%" : null);
        query.setParameter("dep", dep);
        query.setParameter("status", status);
        query.setParameter("eid", eid);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Offer> offers = query.getResultList();
        session.close();
        return offers;
    }

    @Override
    public long countAllOfferByNameMailDepStatus(String text, int dep, int status, int eid) {
        Session session = em.unwrap(Session.class);
        Employee e = session.get(Employee.class, eid);

        if (e.getRole() == HrRole.ROLE_RECRUITER) {
            return em.createQuery(
                            "SELECT COUNT(o) FROM Offer o " +
                                    "JOIN o.candidate u " +
                                    "JOIN o.department d " +
                                    "JOIN o.statusOffer s " +
                                    "WHERE (:text IS NULL OR u.user.fullname LIKE :text OR u.user.email LIKE :text) " +
                                    "AND (:dep = 0 OR d.id = :dep) " +
                                    "AND (:status = 0 OR s.id = :status) " +
                                    "AND (o.recruiterOwner = :eid)", Long.class)
                    .setParameter("text", text == null ? null : "%" + text + "%")
                    .setParameter("dep", dep)
                    .setParameter("status", status)
                    .setParameter("eid", eid)
                    .getSingleResult();
        }

        if (e.getRole() == HrRole.ROLE_ADMINISTRATOR || e.getRole() == HrRole.ROLE_MANAGER) {
            return em.createQuery(
                            "SELECT COUNT(o) FROM Offer o " +
                                    "JOIN o.candidate u " +
                                    "JOIN o.department d " +
                                    "JOIN o.statusOffer s " +
                                    "WHERE (:text IS NULL OR u.user.fullname LIKE :text OR u.user.email LIKE :text) " +
                                    "AND (:dep = 0 OR d.id = :dep) " +
                                    "AND (:status = 0 OR s.id = :status) " +
                                    "AND (o.employee.id = :eid)", Long.class)
                    .setParameter("text", text == null ? null : "%" + text + "%")
                    .setParameter("dep", dep)
                    .setParameter("status", status)
                    .setParameter("eid", eid)
                    .getSingleResult();
        }

        return -1;
    }

    @Override
    public Offer getOfferById(long id) {
        Session session = em.unwrap(Session.class);
        Offer offer = null;
        try {
            offer = session.createQuery("select o from Offer o where o.id = :id", Offer.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return offer;
    }

    @Override
    public List<Offer> getAllOfferByEmployee(int id, Pageable pageable) {
        Session session = em.unwrap(Session.class);

        Employee e = session.get(Employee.class, id);

        List<Offer> offers = new ArrayList<>();

        if (e.getRole() == HrRole.ROLE_RECRUITER) {
            offers = session.createQuery("select o from Offer o where o.recruiterOwner = :id", Offer.class)
                    .setParameter("id", id)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }

        if (e.getRole() == HrRole.ROLE_MANAGER || e.getRole() == HrRole.ROLE_ADMINISTRATOR) {
            offers = session.createQuery("select o from Offer o where o.employee.id = :id", Offer.class)
                    .setParameter("id", id)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }

        session.close();
        return offers;
    }

    @Transactional
    @Override
    public boolean editOffer(int offerid, int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy) {
        Session session = em.unwrap(Session.class);
        Transaction transaction = null;

        try {
            //  transaction = session.beginTransaction();

            Offer offer = session.get(Offer.class, offerid);
            if (offer == null) {
                return false;
            }

            offer.setBasicSalary(salary);
            offer.setContractPeriodFrom(from);
            offer.setContractPeriodTo(to);
            offer.setDueDate(duedate);
            offer.setInterviewNotes(interviewNote);
            offer.setNote(note);
            offer.setRecruiterOwner(recruiterOwner);
            offer.setUpdatedBy(Long.valueOf(updateBy));

            Interview interview = session.get(Interview.class, interviewId);
            if (interview != null) {
                offer.setInterview(interview);
            } else {
                throw new IllegalArgumentException("Interview not found");
            }

            Candidate candidate = session.get(Candidate.class, cid);
            if (candidate != null) {
                offer.setCandidate(candidate);
            } else {
                throw new IllegalArgumentException("Candidate not found");
            }

            Department department = session.get(Department.class, did);
            if (department != null) {
                offer.setDepartment(department);
            } else {
                throw new IllegalArgumentException("Department not found");
            }

            Position position = session.get(Position.class, pid);
            if (position != null) {
                offer.setPosition(position);
            } else {
                throw new IllegalArgumentException("Position not found");
            }

            ContractType contractType = session.get(ContractType.class, coid);
            if (contractType != null) {
                offer.setContractType(contractType);
            } else {
                throw new IllegalArgumentException("ContractType not found");
            }

            Level level = session.get(Level.class, lid);
            if (level != null) {
                offer.setLevel(level);
            } else {
                throw new IllegalArgumentException("Level not found");
            }

            Employee employee = session.get(Employee.class, eid);
            if (employee != null) {
                offer.setEmployee(employee);
            } else {
                throw new IllegalArgumentException("Employee not found");
            }

            session.merge(offer);
            //  transaction.commit();
            return true;

        } catch (Exception e) {
//            if (transaction != null) {
            System.out.println(e.getMessage());
//                transaction.rollback();
//            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    @Override
    public boolean updateStatusOffer(int offerid, int status) {
        Session session = em.unwrap(Session.class);
        Transaction transaction = session.beginTransaction();

        try {
            Offer offer = session.get(Offer.class, offerid);

            if (offer == null) {
                session.close();
                return false;
            }
            offer.setStatusOffer(session.get(StatusOffer.class, status));
            session.merge(offer);
            transaction.commit();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

//    @Override
//    public List<Offer> findAllOffers(Pageable pageable, int id) {
//        System.out.println("findAllOffers");
//        return em.createQuery("select o from Offer o where o.recruiterOwner = :id", Offer.class)
//                .setParameter("id", id)
//                .setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//    }

    @Override
    public long countAllOffers(int id) {
        Session session = em.unwrap(Session.class);
        Employee e = session.get(Employee.class, id);

        if (e.getRole() == HrRole.ROLE_RECRUITER) {

            return em.createQuery("SELECT COUNT(o) FROM Offer o where o.recruiterOwner = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }

        if (e.getRole() == HrRole.ROLE_ADMINISTRATOR || e.getRole() == HrRole.ROLE_MANAGER) {

            return em.createQuery("SELECT COUNT(o) FROM Offer o where o.employee.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }

        return -1;
    }


    @Override
    public Offer getMaxOfferOfCandidate(int cid) {
        try {
            Session session = em.unwrap(Session.class);
            return session.createQuery(
                            "select o from Offer o where o.candidate.id = :id order by o.id desc", Offer.class)
                    .setParameter("id", cid)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null khi có lỗi thay vì để ứng dụng bị crash
        }
    }


//    @Override
//    public List<Offer> getAllOfferOfManager(int mid, Pageable pageable) {
//        Session session = em.unwrap(Session.class);
//        List<Offer> offers = session.createQuery("select o from com.ims_team4.model.Offer o, com.ims_team4.model.Employee e where o.employee.id = e.id and o.employee.id = :id and e.role = :role", Offer.class)
//                .setParameter("id", mid)
//                .setParameter("role", HrRole.ROLE_MANAGER)
//                .setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//        session.close();
//        return offers;
//    }

//    @Override
//    public List<Offer> getAllOfferOfAdmin(int aid, Pageable pageable) {
//        Session session = em.unwrap(Session.class);
//        List<Offer> offers = session.createQuery("select o from com.ims_team4.model.Offer o, com.ims_team4.model.Employee e where o.employee.id = e.id and o.employee.id = :id and e.role = :role", Offer.class)
//                .setParameter("id", aid)
//                .setParameter("role", HrRole.ROLE_ADMINISTRATOR)
//                .setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//        session.close();
//        return offers;
//    }

    @Override
    public boolean createOffer(int salary, LocalDate from, LocalDate to, LocalDate duedate, String interviewNote, int interviewId, String note, int recruiterOwner, int cid, int coid, int did, int eid, int lid, int pid, int updateBy) {
        Session session = em.unwrap(Session.class);
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Offer offer = new Offer();
            offer.setBasicSalary(salary);
            offer.setContractPeriodFrom(from);
            offer.setContractPeriodTo(to);
            offer.setDueDate(duedate);
            offer.setInterviewNotes(interviewNote);
            offer.setNote(note);
            offer.setRecruiterOwner(recruiterOwner);
            offer.setUpdatedBy(Long.valueOf(updateBy));

            // Lấy và thiết lập đối tượng Interview
            Interview interview = session.get(Interview.class, interviewId);
            if (interview != null) {
                offer.setInterview(interview);
            } else {
                throw new IllegalArgumentException("Interview not found");
            }

            Candidate candidate = session.get(Candidate.class, cid);
            if (candidate != null) {
                offer.setCandidate(candidate);
            } else {
                throw new IllegalArgumentException("Candidate not found");
            }

            Department department = session.get(Department.class, did);
            if (department != null) {
                offer.setDepartment(department);
            } else {
                throw new IllegalArgumentException("Department not found");
            }

            // Lấy và thiết lập đối tượng Position
            Position position = session.get(Position.class, pid);
            if (position != null) {
                offer.setPosition(position);
            } else {
                throw new IllegalArgumentException("Position not found");
            }

            // Lấy và thiết lập đối tượng ContractType
            ContractType contractType = session.get(ContractType.class, coid);
            if (contractType != null) {
                offer.setContractType(contractType);
            } else {
                throw new IllegalArgumentException("ContractType not found");
            }

            // Lấy và thiết lập đối tượng Level
            Level level = session.get(Level.class, lid);
            if (level != null) {
                offer.setLevel(level);
            } else {
                throw new IllegalArgumentException("Level not found");
            }

            // Lấy và thiết lập đối tượng Employee
            Employee employee = session.get(Employee.class, eid);
            if (employee != null) {
                offer.setEmployee(employee);
            } else {
                throw new IllegalArgumentException("Employee not found");
            }

            StatusOffer statusOffer = session.get(StatusOffer.class, 1);
            offer.setStatusOffer(statusOffer);

            // Lưu đối tượng Offer mới vào cơ sở dữ liệu
            session.merge(offer);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Offer> getAllOfferFromToOfEid(LocalDate from, LocalDate to, int eid) {
        List<Offer> offers = new ArrayList<>();

        try (Session session = em.unwrap(Session.class)) {
            Employee e = session.get(Employee.class, eid);
            if (e == null) {
                return offers;
            }

            String queryString;
            switch (e.getRole()) {
                case ROLE_RECRUITER:
                    queryString = "SELECT o FROM Offer o WHERE o.recruiterOwner = :eid "
                            + "AND o.contractPeriodFrom <= :to AND o.contractPeriodTo >= :from";
                    break;
                case ROLE_MANAGER:
                case ROLE_ADMINISTRATOR:
                    queryString = "SELECT o FROM Offer o WHERE o.employee.id = :eid "
                            + "AND o.contractPeriodFrom <= :to AND o.contractPeriodTo >= :from";
                    break;
                default:
                    return offers;
            }

            offers = session.createQuery(queryString, Offer.class)
                    .setParameter("eid", eid)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return offers;
    }



    // </editor-fold>
    @NotNull
    @Override
    public <S extends Offer> S save(@NotNull S entity) {
        return null;
    }

    @Override
    @NotNull
    public <S extends Offer> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    @NotNull
    public Optional<Offer> findById(@NotNull Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull Long aLong) {
        return false;
    }

    @Deprecated
    @Override
    @NotNull
    public Iterable<Offer> findAll() {
        return null;
    }

    @Override
    @NotNull
    public Iterable<Offer> findAllById(@NotNull Iterable<Long> longs) {
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
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }


}

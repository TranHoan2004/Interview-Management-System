package com.ims_team4.repository;

import com.ims_team4.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("""
                FROM Notification n
                WHERE n.user.id=:id
                ORDER BY n.status ASC
            """)
    Page<Notification> getAllNotificationByUserID(@Param("id") Long id, Pageable pageable);

    @Query("""
                FROM Notification n
                WHERE n.user.id=:id
            """)
    List<Notification> getAllByUserID(Long id);

    @Query("""
            FROM Notification n
            WHERE n.id=:id
            """)
    Notification getByID(@Param("id") Long id);
}

package com.ims_team4.repository;

import com.ims_team4.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    // Duc Long getALlNotificaionByUserId
    List<Notification> getAllNotificationByUserId();

    Notification getByUserId(Long id);
}

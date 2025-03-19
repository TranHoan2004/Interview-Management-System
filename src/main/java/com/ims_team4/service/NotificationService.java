package com.ims_team4.service;

import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.model.Notification;

import java.util.List;

// Duc Long
public interface NotificationService {
    List<Notification> getAllNotification();

    void saveNewNotification(NotificationDTO notification);

    NotificationDTO getNotificationDTOByUserID(Long id) throws Exception;
}


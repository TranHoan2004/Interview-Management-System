package com.ims_team4.service;

import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.model.Notification;
import org.springframework.data.domain.Page;

import java.util.List;

// Duc Long
public interface NotificationService {
    List<NotificationDTO> getAllNotificationByUserID(Long id) throws Exception;

    void saveNewNotification(NotificationDTO notification);

    Page<NotificationDTO> getPaginatedNotificationDTOByUserID(Long id, int start, int maxResult);

    NotificationDTO getNotificationDTOByID(Long id) throws Exception;

    NotificationDTO updateNotificationDTO(Long id);
}


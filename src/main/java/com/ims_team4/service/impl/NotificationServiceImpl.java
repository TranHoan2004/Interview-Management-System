package com.ims_team4.service.impl;

import com.ims_team4.model.Notification;
import com.ims_team4.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Duc Long
public class NotificationServiceImpl implements NotificationService {

    @Override
    public List<Notification> getAllNotification() {
        return List.of();
    }
}

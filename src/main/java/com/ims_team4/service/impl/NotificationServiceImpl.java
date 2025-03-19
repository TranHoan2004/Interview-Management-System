package com.ims_team4.service.impl;

import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.model.Notification;
import com.ims_team4.repository.NotificationRepository;
import com.ims_team4.repository.impl.NotificationRepositoryImpl;
import com.ims_team4.service.NotificationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Service
// HoanTX
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repo;
    private final Logger logger = Logger.getLogger(NotificationServiceImpl.class.getName());

    public NotificationServiceImpl(NotificationRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public List<Notification> getAllNotification() {
        return List.of();
    }

    @Override
    public void saveNewNotification(@NotNull NotificationDTO notification) {
        repo.save(convert(notification));
    }

    @Override
    public NotificationDTO getNotificationDTOByUserID(Long id) throws Exception {
        logger.info("getNotificationDTOByUserID in service impl");
        Notification noti = repo.getByUserId(id);
        if (noti == null) {
            throw new Exception("This user has not had a notification container yet");
        }
        return convert(repo.getByUserId(id));
    }

    // <editor-fold> desc="convert entity"
    private NotificationDTO convert(@NotNull Notification notification) {
        return NotificationDTO.builder()
                .userID(notification.getId())
                .title(notification.getTitle())
                .build();
    }

    private Notification convert(@NotNull NotificationDTO notification) {
        return Notification.builder()
                .id(notification.getUserID())
                .title(notification.getTitle())
                .build();
    }
    // </editor-fold>
}

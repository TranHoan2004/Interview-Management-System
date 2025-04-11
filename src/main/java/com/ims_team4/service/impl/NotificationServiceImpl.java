package com.ims_team4.service.impl;

import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.model.Notification;
import com.ims_team4.model.Users;
import com.ims_team4.repository.NotificationRepository;
import com.ims_team4.service.NotificationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
// HoanTX
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repo;
    private final UserServiceImpl userService;
    private final Logger logger = Logger.getLogger(NotificationServiceImpl.class.getName());

    public NotificationServiceImpl(NotificationRepository repo, UserServiceImpl userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Override
    public List<NotificationDTO> getAllNotificationByUserID(Long id) throws Exception {
        List<Notification> op = repo.getAllByUserID(id);
        if (!op.isEmpty()) {
            return op.stream().map(this::convert).collect(Collectors.toList());
        }
        throw new Exception("No notification found with id " + id);
    }

    @Override
    public void saveNewNotification(@NotNull NotificationDTO notification) {
        Notification noti = convert(notification);
        noti.setUser(userService.getUser(notification.getUserID()));
        repo.save(noti);
    }

    @Override
    public Page<NotificationDTO> getPaginatedNotificationDTOByUserID(Long id, int start, int maxResult) {
        logger.info("getNotificationDTOByUserID in service impl");
        Pageable page = PageRequest.of(start, maxResult);
        return repo.getAllNotificationByUserID(id, page).map(this::convert);
    }

    @Override
    public NotificationDTO getNotificationDTOByID(Long id) throws Exception {
        return convert(repo.findById(id).orElseThrow(() -> new Exception("Notification not found")));
    }

    @Override
    public NotificationDTO updateNotificationDTO(@NotNull Long id) {
        Notification noti = repo.getByID(id);
        noti.setStatus(true);
        repo.save(noti);
        return convert(noti);
    }

    // <editor-fold> desc="convert entity"
    private NotificationDTO convert(@NotNull Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .userID(notification.getUser().getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .link(notification.getLink())
                .status(notification.isStatus())
                .build();
    }

    private Notification convert(@NotNull NotificationDTO notification) {
        Notification.NotificationBuilder builder = Notification.builder()
                .title(notification.getTitle())
                .message(notification.getMessage())
                .link(notification.getLink())
                .status(notification.getStatus());
        if (notification.getId() != null) {
            builder.id(notification.getId());
        }
        return builder.build();
    }
    // </editor-fold>
}

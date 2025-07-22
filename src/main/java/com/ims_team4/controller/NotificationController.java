package com.ims_team4.controller;

import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.dto.request.MessageRequest;
import com.ims_team4.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
// HoanTX
// Chú ý: Tạo phương thức mới để sử dụng, tránh ghi đè phương thuc cũ
public class NotificationController {
    private final NotificationService nSrv;

    public NotificationController(NotificationService nSrv) {
        this.nSrv = nSrv;
    }

    // Khi trang view goi den phuong thuc nay, phuong thuc se tao thong bao moi va tra ve noi dung thong bao do
    // ve trang view hien tai
    @PostMapping("/notification/create")
    @SendTo("/topic/messages")
    @MessageMapping("/application")
    public MessageRequest createNotification(final MessageRequest message) {
        nSrv.saveNewNotification(getNotificationDTO(message));
        return message;
    }

    private NotificationDTO getNotificationDTO(@NotNull final MessageRequest message) {
        log.info("create notification");
        return NotificationDTO.builder()
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .userID(Long.valueOf(message.getMessage().getTo()))
                .title(message.getTitle())
                .link(message.getMessage().getContent().get("link"))
                .message(message.getMessage().getContent().get("message"))
                .status(false)
                .build();
    }

    /*
     * Phương thức này ném thông báo ra view tự động
     * Khi load trang, ajax se tu dong goi den phuong thuc nay
     */
    @PostMapping("/notification/list")
    public ResponseEntity<List<NotificationDTO>> renderAllNotifications(HttpSession session) {
        List<NotificationDTO> allNotification;
        List<NotificationDTO> pseudoList = new ArrayList<>();
        EmployeeDTO emp = (EmployeeDTO) session.getAttribute("account");
        Long id = emp.getUserID();
        try {
            allNotification = nSrv.getAllNotificationByUserID(id);
            pseudoList = allNotification
                    .stream()
                    .sorted(Comparator.comparing(NotificationDTO::getStatus))
                    .toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.ok(pseudoList);
    }

    @GetMapping("/notification/details")
    public String renderNotificationDetails(@RequestParam("id") Long id, Model model) {
        try {
            nSrv.updateNotificationDTO(id);
            NotificationDTO notification = nSrv.getNotificationDTOByID(id);
            model.addAttribute("notification",
                    notification);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "notification/notification-details";
    }

    @GetMapping("/notification/list")
    public String renderPaginatedNotificationList(Model model, HttpSession session,
                                                  @RequestParam(value = "index", defaultValue = "0") int index // show the start point of list
    ) {
        int size;
        EmployeeDTO emp = (EmployeeDTO) session.getAttribute("account");
        Long id = emp.getUserID();
        Page<NotificationDTO> list;
        int newIndex = 0;
        if (index != 0) {
            newIndex = index - 1;
        }
        try {
            list = nSrv.getPaginatedNotificationDTOByUserID(id, newIndex, 3);
            size = list.getTotalPages();
            model.addAttribute("list", list);
            model.addAttribute("size", size);
            model.addAttribute("currentPage", index);
            log.info("current page: {}", index);
            log.info("size: {}", size);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "notification/notification";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/notification/update/{id}")
    @ResponseBody
    public String updateNotificationAndRender(@PathVariable("id") Long id) {
        log.info("Go here");
        NotificationDTO noti = nSrv.updateNotificationDTO(id);
        return noti.getLink();
    }
}

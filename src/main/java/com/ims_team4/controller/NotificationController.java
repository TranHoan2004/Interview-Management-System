package com.ims_team4.controller;

import com.ims_team4.config.Constants;
import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/notification")
public class NotificationController implements Constants.Link {
    private final NotificationService nSrv;
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = Logger.getLogger(NotificationController.class.getName());

    public NotificationController(NotificationService nSrv, SimpMessagingTemplate messagingTemplate) {
        this.nSrv = nSrv;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create")
    public String create(@RequestParam("content") String content,
                         @RequestParam("employee") Long employeeID,
                         @RequestParam("title") String title,
                         RedirectAttributes model) {
        logger.info(content);
        logger.info(employeeID.toString());
        logger.info(title);
        logger.info("create notification");
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        String url = NOTIFICATION_LINK + "/details?id=" + id;
        NotificationDTO noti = NotificationDTO.builder()
                .id(id)
                .userID(employeeID)
                .title(title)
                .link(url)
                .message(content)
                .status(false)
                .build();
        nSrv.saveNewNotification(noti);

        logger.info("send data to javascript");
        Map<String, Object> message = new HashMap<>();
        message.put("content", content);
        message.put("employee", employeeID);
        messagingTemplate.convertAndSend("/topic/notifications", message);
        return "index";
    }
}

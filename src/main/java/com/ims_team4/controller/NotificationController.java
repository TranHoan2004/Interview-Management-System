package com.ims_team4.controller;

import com.ims_team4.config.Constants;
import com.ims_team4.dto.Message;
import com.ims_team4.dto.NotificationDTO;
import com.ims_team4.dto.request.MessageRequest;
import com.ims_team4.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class NotificationController implements Constants.Link {
    private final NotificationService nSrv;
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = Logger.getLogger(NotificationController.class.getName());

    public NotificationController(NotificationService nSrv, SimpMessagingTemplate messagingTemplate) {
        this.nSrv = nSrv;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/notification/create")
    @SendTo("/topic/messages")
    public String create(@RequestParam("content") String content,
            @RequestParam("employee") Long employeeID,
            @RequestParam("title") String title,
            HttpServletRequest request) {
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

        // Map<String, Object> message = new HashMap<>();
        // message.put("content", content);
        // message.put("employee", employeeID);
        // messagingTemplate.convertAndSend("/topic/notifications", message);

        return "index";
    }

    // use to test
    // Tạo phương thức mới để sử dụng, không nên dùng phương thức mẫu này
    @MessageMapping("/application")
    @SendTo("/topic/messages")
    public MessageRequest send(final MessageRequest messsage) {
        logger.info(messsage.getMessage().getFrom() + " " + messsage.getMessage().getTo() + " "
                + messsage.getMessage().getText());
        logger.info(messsage.getTitle());
        logger.info(messsage.getUserID().toString());
        // Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        // String url = NOTIFICATION_LINK + "/details?id=" + id;
        // NotificationDTO noti = NotificationDTO.builder()
        // .id(id)
        // .userID(Long.parseLong(messsage.getTo()))
        // .title(title)
        // .link(url)
        // .message(messsage.getText())
        // .status(false)
        // .build();
        // nSrv.saveNewNotification(noti);
        logger.info(messsage.toString());
        return messsage;
    }
}

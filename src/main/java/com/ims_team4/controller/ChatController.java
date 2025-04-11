package com.ims_team4.controller;

import com.ims_team4.controller.utils.UrlIdEncoder;
import com.ims_team4.dto.ChatDTO;
import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.service.ChatDetailService;
import com.ims_team4.service.ChatService;
import com.ims_team4.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
// Duc Long
public class ChatController {
    private final ChatService chatService;
    private final ChatDetailService chatDetailService;
    private final EmployeeService employeeService;

    public ChatController(ChatService chatService, ChatDetailService chatDetailService, EmployeeService employeeService) {
        this.chatService = chatService;
        this.chatDetailService = chatDetailService;
        this.employeeService = employeeService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("rid") String ridHash, @RequestParam("mid") String midHash, @RequestParam("role") String role, Model model) {
        int rid = UrlIdEncoder.decodeId(ridHash);
        int mid = UrlIdEncoder.decodeId(midHash);
        int chatId = chatService.getChatIdByRecruiterAndManager(rid, mid);
        if (chatId == -1) {
            chatId = chatService.insertChat(rid, mid);
        }
        List<ChatDTO> listC1 = chatService.getAllChatOfRecruiter(rid);
        List<ChatDTO> listC2 = chatService.getAllChatOfManager(mid);
        List<ChatDetailDTO> listL = chatDetailService.getLastestChatDetailOfRecruiterAndManager();
        EmployeeDTO recruiter = employeeService.getEmployeeById(rid);
        EmployeeDTO manager = employeeService.getEmployeeById(mid);
        String username = "";
        HrRole roleR = HrRole.valueOf("ROLE_RECRUITER");
        if (role.equals(roleR.toString())) {
            username = recruiter.getWorkingName();
        }
        HrRole roleM = HrRole.valueOf("ROLE_MANAGER");
        if (role.equals(roleM.toString())) {
            username = manager.getWorkingName();
        }
        List<EmployeeDTO> listManager = employeeService.getAllEmployeeByRole(roleM);
        List<EmployeeDTO> listRecruiter = employeeService.getAllEmployeeByRole(roleR);
        List<ChatDTO> listC = chatService.getAllChat();
        List<EmployeeDTO> listE = employeeService.getAllEmployee();
        List<ChatDetailDTO> listCd = chatDetailService.getAllChatDetailsByChatId(chatId);
        model.addAttribute("recruiter", recruiter);
        model.addAttribute("manager", manager);
        model.addAttribute("listE", listE);
        model.addAttribute("listC", listC);
        model.addAttribute("listCd", listCd);
        model.addAttribute("conversationId", chatId);
        model.addAttribute("username", username);
        model.addAttribute("listC1", listC1);
        model.addAttribute("listC2", listC2);
        model.addAttribute("listL", listL);
        model.addAttribute("listManager", listManager);
        model.addAttribute("listRecruiter", listRecruiter);
        model.addAttribute("rid", rid);
        model.addAttribute("mid", mid);
        return "recruiter-features/chat";
    }
}

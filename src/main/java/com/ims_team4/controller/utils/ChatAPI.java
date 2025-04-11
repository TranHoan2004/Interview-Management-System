package com.ims_team4.controller.utils;

import com.ims_team4.dto.ChatDTO;
import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.model.utils.HrRole;
import com.ims_team4.service.ChatDetailService;
import com.ims_team4.service.ChatService;
import com.ims_team4.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
// Duc Long
public class ChatAPI {
    private final ChatService chatService;
    private final ChatDetailService chatDetailService;
    private final EmployeeService employeeService;

    public ChatAPI(ChatService chatService, ChatDetailService chatDetailService, EmployeeService employeeService) {
        this.chatService = chatService;
        this.chatDetailService = chatDetailService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<ChatResponse> getChatConversation(
            @RequestParam("rid") String ridHash,
            @RequestParam("mid") String midHash,
            @RequestParam("role") String role) {

        try {
            int rid = UrlIdEncoder.decodeId(ridHash);
            int mid = UrlIdEncoder.decodeId(midHash);

            int chatId = chatService.getChatIdByRecruiterAndManager(rid, mid);
            if (chatId == -1) {
                chatId = chatService.insertChat(rid, mid);
            }

            List<ChatDTO> recruiterChats = chatService.getAllChatOfRecruiter(rid);
            List<ChatDTO> managerChats = chatService.getAllChatOfManager(mid);
            List<ChatDetailDTO> latestChats = chatDetailService.getLastestChatDetailOfRecruiterAndManager();
            EmployeeDTO recruiter = employeeService.getEmployeeById(rid);
            EmployeeDTO manager = employeeService.getEmployeeById(mid);

            String username = "";
            HrRole roleR = HrRole.valueOf("ROLE_RECRUITER");
            HrRole roleM = HrRole.valueOf("ROLE_MANAGER");

            if (role.equals(roleR.toString())) {
                username = recruiter.getWorkingName();
            } else if (role.equals(roleM.toString())) {
                username = manager.getWorkingName();
            }

            List<EmployeeDTO> managers = employeeService.getAllEmployeeByRole(roleM);
            List<EmployeeDTO> recruiters = employeeService.getAllEmployeeByRole(roleR);
            List<ChatDetailDTO> chatDetails = chatDetailService.getAllChatDetailsByChatId(chatId);

            ChatResponse response = new ChatResponse();
            response.setRecruiter(recruiter);
            response.setManager(manager);
            response.setConversationId(chatId);
            response.setUsername(username);
            response.setRecruiterChats(recruiterChats);
            response.setManagerChats(managerChats);
            response.setLatestChats(latestChats);
            response.setManagers(managers);
            response.setRecruiters(recruiters);
            response.setChatDetails(chatDetails);
            response.setRid(rid);
            response.setMid(mid);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        } catch (Exception e) {
            ChatResponse errorResponse = new ChatResponse();
            errorResponse.setError("Failed to load chat: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
    }
}

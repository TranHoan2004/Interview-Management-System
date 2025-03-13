package com.ims_team4.service;

import com.ims_team4.dto.ChatDTO;
import com.ims_team4.model.Chat;

import java.util.List;

public interface ChatService {
    List<ChatDTO> getAllChat();
    List<ChatDTO> getAllChatOfRecruiter(int recruiterId);
    List<ChatDTO> getAllChatOfManager(int managerId);
    int insertChat(int rid, int mid);
    ChatDTO getChatById(int id);
    int getChatIdByRecruiterAndManager(int rid, int mid);
}

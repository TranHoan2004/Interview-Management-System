package com.ims_team4.service.impl;

import com.ims_team4.dto.ChatDTO;
import com.ims_team4.model.Chat;
import com.ims_team4.repository.ChatRepository;
import com.ims_team4.service.ChatService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatDTO> getAllChat() {
        return chatRepository.getAllChat().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> getAllChatOfRecruiter(int recruiterId) {
        return chatRepository.getAllChatOfRecruiter(recruiterId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> getAllChatOfManager(int managerId) {
        return chatRepository.getAllChatOfManager(managerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int insertChat(int rid, int mid) {
        return chatRepository.insertChat(rid, mid);
    }

    @Override
    public ChatDTO getChatById(int id) {
        return convertToDTO(chatRepository.getChatById(id));
    }

    @Override
    public int getChatIdByRecruiterAndManager(int rid, int mid) {
        return chatRepository.getChatIdByRecruiterAndManager(rid, mid);
    }

    public ChatDTO convertToDTO(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChatId(chat.getId());
        if (chat.getManager() != null) {
            chatDTO.setManagerId(chat.getManager().getId());
        } else {
            chatDTO.setManagerId(-1);
        }

        // Tương tự kiểm tra cho recruiter
        if (chat.getRecruiter() != null) {
            chatDTO.setRecuiterId(chat.getRecruiter().getId());
        } else {
            chatDTO.setRecuiterId(-1);
        }

        return chatDTO;
    }
}

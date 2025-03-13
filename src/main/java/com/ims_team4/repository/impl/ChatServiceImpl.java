package com.ims_team4.repository.impl;

import com.ims_team4.dto.BenefitDTO;
import com.ims_team4.dto.ChatDTO;
import com.ims_team4.model.Benefit;
import com.ims_team4.model.Chat;
import com.ims_team4.repository.ChatRepository;
import com.ims_team4.service.ChatService;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

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

    private ChatDTO convertToDTO(@NotNull Chat chat) {
        return ChatDTO.builder()
                .chatId(chat.getId())
                .managerId(chat.getManager().getId())
                .recuiterId(chat.getRecruiter().getId())
                .build();
    }
}

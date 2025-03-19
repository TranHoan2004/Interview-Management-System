package com.ims_team4.service.impl;

import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Chat;
import com.ims_team4.model.ChatDetail;
import com.ims_team4.model.Users;
import com.ims_team4.repository.ChatDetailRepository;
import com.ims_team4.service.ChatDetailService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatDetailServiceImpl implements ChatDetailService {

    private ChatDetailRepository chatDetailRepository;


    public ChatDetailServiceImpl(ChatDetailRepository chatDetailRepository) {
        this.chatDetailRepository = chatDetailRepository;
    }


    private ChatDetailDTO convertToDTO(@NotNull ChatDetail chatDetail) {
        return ChatDetailDTO.builder()
                .chatDetailId(chatDetail.getId())
                .ChatID(chatDetail.getChat().getId())
                .Message(chatDetail.getMessage())
                .Sender(chatDetail.getSender())
                .Timestamp(chatDetail.getTimestamp())
                .build();
    }

    @Override
    public List<ChatDetailDTO> getAllChatDetail() {
        return chatDetailRepository.getAllChatDetail().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDetailDTO> getLastestChatDetailOfRecruiterAndManager() {
        return chatDetailRepository.getLastestChatDetailOfRecruiterAndManager().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDetailDTO> getFirstChatDetailOfRecruiter(int rid) {
        return chatDetailRepository.getFirstChatDetailOfRecruiter(rid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDetailDTO> getFirstChatDetailOfManager(int mid) {
        return chatDetailRepository.getFirstChatDetailOfManager(mid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDetailDTO> getAllChatDetailOfRecruiter(int rid) {
        return chatDetailRepository.getAllChatDetailOfRecruiter(rid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDetailDTO> getAllChatDetailsByChatId(int chatid) {
        return chatDetailRepository.getAllChatDetailsByChatId(chatid).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveChatDetail(ChatDetailDTO chatDetail) {
        if (chatDetail == null) {
            throw new IllegalArgumentException("ChatDetailDTO không được null");
        }
        chatDetailRepository.saveChatDetail(convertToEntity(chatDetail));
    }

    private ChatDetail convertToEntity(@NotNull ChatDetailDTO chatDetailDTO) {
        ChatDetail chatDetail;

        if (chatDetailDTO.getChatDetailId() > 0) {
            chatDetail = chatDetailRepository.findById(chatDetailDTO.getChatDetailId())
                    .orElseThrow(() -> new RuntimeException("ChatDetail không tồn tại"));
        } else {
            chatDetail = new ChatDetail();
        }

        chatDetail.setId(chatDetailDTO.getChatDetailId());

        if (chatDetailDTO.getChatID() <= 0) {
            throw new IllegalArgumentException("ChatID không hợp lệ");
        }

        Chat chat = new Chat();
        chat.setId(chatDetailDTO.getChatID());
        chatDetail.setChat(chat);

        chatDetail.setMessage(chatDetailDTO.getMessage());
        chatDetail.setSender(chatDetailDTO.getSender());
        chatDetail.setTimestamp(chatDetailDTO.getTimestamp());

        return chatDetail;
    }

}

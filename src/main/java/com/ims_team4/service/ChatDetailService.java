package com.ims_team4.service;

import com.ims_team4.dto.ChatDetailDTO;
import com.ims_team4.model.ChatDetail;

import java.util.List;

public interface ChatDetailService {
    List<ChatDetailDTO> getAllChatDetail();
    List<ChatDetailDTO> getLastestChatDetailOfRecruiterAndManager();
    List<ChatDetailDTO> getFirstChatDetailOfRecruiter(int rid);
    List<ChatDetailDTO> getFirstChatDetailOfManager(int mid);
    List<ChatDetailDTO> getAllChatDetailOfRecruiter(int rid);
    List<ChatDetailDTO> getAllChatDetailsByChatId(int chatid);
    void saveChatDetail(ChatDetailDTO chatDetail);
}

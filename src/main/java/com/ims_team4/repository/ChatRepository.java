package com.ims_team4.repository;

import com.ims_team4.model.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> getAllChat();
    List<Chat> getAllChatOfRecruiter(int recruiterId);
    List<Chat> getAllChatOfManager(int managerId);
    int insertChat(int rid, int mid);
    Chat getChatById(int id);
    int getChatIdByRecruiterAndManager(int rid, int mid);
}

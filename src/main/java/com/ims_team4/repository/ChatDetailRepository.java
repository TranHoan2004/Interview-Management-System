package com.ims_team4.repository;

import com.ims_team4.model.ChatDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatDetailRepository extends CrudRepository<ChatDetail, Long> {
    List<ChatDetail> getAllChatDetail();
    List<ChatDetail> getLastestChatDetailOfRecruiterAndManager();
    List<ChatDetail> getFirstChatDetailOfRecruiter(int rid);
    List<ChatDetail> getFirstChatDetailOfManager(int mid);
    List<ChatDetail> getAllChatDetailOfRecruiter(int rid);
    List<ChatDetail> getAllChatDetailsByChatId(int chatid);
    void saveChatDetail(ChatDetail chatDetail);
    void insertChat(int cid, String msg);

}

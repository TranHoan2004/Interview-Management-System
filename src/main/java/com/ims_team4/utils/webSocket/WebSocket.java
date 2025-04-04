package com.ims_team4.utils.webSocket;

import com.ims_team4.model.Chat;
import com.ims_team4.model.ChatDetail;
import com.ims_team4.repository.impl.ChatDetailRepositoryImpl;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@ServerEndpoint("/websocket/{conversationId}/{role}")
public class WebSocket {
    private static final Map<String, Set<Session>> conversations = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(@PathParam("conversationId") String conversationId,
            @PathParam("role") String role, @NotNull Session session) {
        // Store the role in the session properties
        session.getUserProperties().put("role", role);
        conversations.computeIfAbsent(conversationId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
    }

    @OnMessage
    public void onMessage(@PathParam("conversationId") String conversationId, String msg, @NotNull Session session) {
        // Retrieve the sender's role from the session properties
        String role = (String) session.getUserProperties().get("role");

        // Create and populate ChatDetail object
        ChatDetail cd = new ChatDetail();
        Chat chat = new Chat();
        chat.setId(Integer.parseInt(conversationId));
        cd.setChat(chat);
        cd.setMessage(msg);
        cd.setSender(role);
        cd.setTimestamp(new Date());

        // Save chat details to the database
        ChatDetailRepositoryImpl chatDetailRepository = SpringContext.getBean(ChatDetailRepositoryImpl.class);
        try {
            chatDetailRepository.saveChatDetail(cd);
            System.out.println("Message saved to DB for conversationId: " + conversationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        broadcast(conversationId, msg, session);
    }

    @OnClose
    public void onClose(@PathParam("conversationId") String conversationId, Session session) {
        Set<Session> conversationSessions = conversations.get(conversationId);
        if (conversationSessions != null) {
            conversationSessions.remove(session);
            if (conversationSessions.isEmpty()) {
                conversations.remove(conversationId);
            }
        }
    }

    private void broadcast(String conversationId, String message, Session senderSession) {
        Set<Session> conversationSessions = conversations.get(conversationId);
        if (conversationSessions != null) {
            for (Session client : conversationSessions) {
                if (client.isOpen() && !client.equals(senderSession)) {
                    try {
                        client.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        System.err.println("Error broadcasting message: " + e.getMessage());
                    }
                }
            }
        }
    }
}

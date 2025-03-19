package com.ims_team4.utils.webSocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        session.sendMessage(new TextMessage("Echo: " + payload));
    }
}

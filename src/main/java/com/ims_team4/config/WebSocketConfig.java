package com.ims_team4.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.io.File;
import java.io.FileInputStream;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {@Override
    public void configureMessageBroker(@NotNull MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Kênh gửi tin nhắn
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(@NotNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    public static void main(String[] args) {
        File file = new File("src/main/resources/static/images/Facebook_Logo_(2019).png");
        try {
            byte[] str = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            System.out.println(fis.read(str));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        File file = new File("src/main/resources/static/images/Facebook_Logo_(2019).png");
//        try {
//            byte[] str = new byte[(int) file.length()];
//            FileInputStream fis = new FileInputStream(file);
//            System.out.println(fis.read(str));
//            OutputStream os = response.getOutputStream();
//            os.write(str);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}

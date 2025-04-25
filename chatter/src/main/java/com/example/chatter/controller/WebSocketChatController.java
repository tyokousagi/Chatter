package com.example.chatter.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chatter.DiscordMessenger;
import com.example.chatter.entity.Authentication;
import com.example.chatter.entity.Messages;
import com.example.chatter.repository.AuthenticationMapper;
import com.example.chatter.service.MessageService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final AuthenticationMapper authenticationMapper;
    
    @Autowired
    private DiscordMessenger discordMessenger;
    
    @Value("${app.discord.webhook.url}")
    private String discordWebhookUrl;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage incomingMessage) throws Exception {
        int roomId = (incomingMessage.getRoomId() != null) ? incomingMessage.getRoomId() : 1;

        Messages messageEntity = new Messages();
        messageEntity.setChat_room_id(roomId);
        messageEntity.setMessage(incomingMessage.getMessage());
        messageEntity.setCreated_at(LocalDateTime.now());
        messageEntity.setIs_read(false);

        Authentication authEntity = authenticationMapper.selectByUsername(incomingMessage.getUser().getUsername());
        messageEntity.setUser(authEntity);

        messageService.sendMessage(messageEntity);

        ChatMessage outgoingMessage = new ChatMessage();
        outgoingMessage.setRoomId(roomId);

        ChatMessage.User user = new ChatMessage.User();
        user.setUsername(authEntity != null ? authEntity.getUsername() : "Unknown");
        user.setIconUrl(authEntity != null ? authEntity.getIconUrl() : null);
        outgoingMessage.setUser(user);
        outgoingMessage.setMessage(incomingMessage.getMessage());

        messagingTemplate.convertAndSend("/topic/chatrooms/" + roomId, outgoingMessage);
        System.out.println("Sent message to /topic/chatrooms/" + roomId + ": " + outgoingMessage);

        System.out.println("Discord Webhook URL: " + discordWebhookUrl);

        if (incomingMessage.getMessage() != null && !incomingMessage.getMessage().isEmpty()) {
            discordMessenger.sendDiscordMessage(discordWebhookUrl, incomingMessage.getMessage());
        }
    }

    @Data
    public static class ChatMessage {
        private Integer roomId;
        private User user;
        private String message;

        @Data
        public static class User {
            private String username;
            private String iconUrl;
        }
    }
}

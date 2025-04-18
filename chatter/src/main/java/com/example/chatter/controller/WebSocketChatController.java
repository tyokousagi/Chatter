package com.example.chatter.controller;

import java.time.LocalDateTime;
import java.util.List;

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
    
    // Discord 送信用
    @Autowired
    private DiscordMessenger discordMessenger;
    
    // プロパティファイルから Discord Webhook URL を取得
    @Value("${app.discord.webhook.url}")
    private String discordWebhookUrl;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage incomingMessage) throws Exception {
        // roomId が指定されていない場合はデフォルトで 1 とする
        int roomId = (incomingMessage.getRoomId() != null) ? incomingMessage.getRoomId() : 1;

        // DB 保存用エンティティの生成
        Messages messageEntity = new Messages();
        messageEntity.setChat_room_id(roomId);
        messageEntity.setMessage(incomingMessage.getMessage());
        messageEntity.setCreated_at(LocalDateTime.now());
        messageEntity.setIs_read(false);

        // 受信メッセージのユーザー名から DB 上の最新ユーザー情報を取得（iconUrl なども含む）
        Authentication authEntity = authenticationMapper.selectByUsername(incomingMessage.getUser().getUsername());
        messageEntity.setUser(authEntity);

        // メッセージを DB に保存
        messageService.sendMessage(messageEntity);

        // 送信用 DTO を新たに作成
        ChatMessage outgoingMessage = new ChatMessage();
        outgoingMessage.setRoomId(roomId);

        ChatMessage.User user = new ChatMessage.User();
        user.setUsername(authEntity != null ? authEntity.getUsername() : "Unknown");
        user.setIconUrl(authEntity != null ? authEntity.getIconUrl() : null);
        outgoingMessage.setUser(user);
        outgoingMessage.setMessage(incomingMessage.getMessage());

        // WebSocket でクライアントに送信
        messagingTemplate.convertAndSend("/topic/chatrooms/" + roomId, outgoingMessage);
        System.out.println("Sent message to /topic/chatrooms/" + roomId + ": " + outgoingMessage);

        // ===== Discord 送信用の処理を追加 =====
        // デバッグ用出力
        System.out.println("Discord Webhook URL: " + discordWebhookUrl);

        // メッセージが空でなければ Discord に送信
        if (incomingMessage.getMessage() != null && !incomingMessage.getMessage().isEmpty()) {
            discordMessenger.sendDiscordMessage(discordWebhookUrl, incomingMessage.getMessage());
        }
        
        // Discord通知のみ残し、プッシュ通知と通知機能は削除されました
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

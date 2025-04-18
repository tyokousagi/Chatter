package com.example.chatter.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.chatter.DiscordMessenger;
import com.example.chatter.config.ApplicationContextProvider;
import com.example.chatter.entity.Authentication;
import com.example.chatter.entity.ChatRooms;
import com.example.chatter.entity.Messages;
import com.example.chatter.repository.AuthenticationMapper;
import com.example.chatter.service.ChatRoomService;
import com.example.chatter.service.MessageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatterController {

    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final AuthenticationMapper authenticationMapper;
    // WebSocket メッセージ送信用テンプレート
    private final SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private DiscordMessenger discordMessenger;

    @GetMapping("/")
    public String menu(Model model) {
        model.addAttribute("chatRooms", chatRoomService.findAllChatRoom());
        return "menu";
    }

    @PostMapping("/chatrooms")
    public String createChatRoom(@ModelAttribute ChatRooms chatRoom) {
        chatRoomService.createChatRoom(chatRoom);
        return "redirect:/";
    }

    @GetMapping("/chatrooms/{roomId}")
    public String chatRoom(@PathVariable int roomId, Model model) {
        ChatRooms chatRoom = chatRoomService.findById(roomId);
        List<Messages> messages = messageService.findMessagesByRoomId(roomId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new Messages());
        return "chatroom";
    }

    // ※従来の HTTP POST を利用するメッセージ送信（リロードが発生します）
    @PostMapping("/chatrooms/{roomId}/messages")
    public String sendMessage(@PathVariable int roomId, @ModelAttribute Messages newMessage) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            username = userDetails.getUsername();
        }
        Authentication userEntity = authenticationMapper.selectByUsername(username);

        newMessage.setChat_room_id(roomId);
        newMessage.setUser(userEntity);
        newMessage.setCreated_at(LocalDateTime.now());
        messageService.sendMessage(newMessage);

        // HTTP POST 経由の場合も WebSocket ブロードキャストを実施
        messagingTemplate.convertAndSend("/topic/chatrooms/" + roomId, newMessage);

        // Discord Webhook への送信処理
        String discordWebhookUrl = System.getenv("DISCORD_WEBHOOK_URL");
        if (discordWebhookUrl == null || discordWebhookUrl.isEmpty()) {
            // 環境変数から取得できない場合は、アプリケーションプロパティから取得
            org.springframework.core.env.Environment env = com.example.chatter.config.ApplicationContextProvider.getApplicationContext().getEnvironment();
            discordWebhookUrl = env.getProperty("app.discord.webhook.url");
            System.err.println("DISCORD_WEBHOOK_URL is not set in environment variables. Using property value.");
        }
        String message = newMessage.getMessage();
        if (message != null && !message.isEmpty()) {
            discordMessenger.sendDiscordMessage(discordWebhookUrl, message);
        }
        return "redirect:/chatrooms/" + roomId;
    }
}

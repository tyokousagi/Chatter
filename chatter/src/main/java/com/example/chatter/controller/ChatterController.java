package com.example.chatter.controller;

import java.util.List;

import org.apache.ibatis.javassist.tools.framedump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.chatter.DiscordMessenger;
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
    @Autowired
    private DiscordMessenger discordMessenger;

    @GetMapping("/")
    public String menu(Model model) {
        model.addAttribute("chatRooms", chatRoomService.findAllChatRoom());
        return "menu";
    }

    @PostMapping("/chatrooms")
    public String createChatRoom(@ModelAttribute ChatRooms chatRoom) {
        chatRoomService.createChatRoom(chatRoom); // サービス層で作成処理を行う
        return "redirect:/"; // メニュー画面にリダイレクト
    }

    @GetMapping("/chatrooms/{roomId}")
    public String chatRoom(@PathVariable int roomId, Model model) {
        ChatRooms chatRoom = chatRoomService.findById(roomId); // chatRoomServiceにfindByIdメソッドを追加
        List<Messages> messages = messageService.findMessagesByRoomId(roomId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new Messages());
    return "chatroom";
}
    @PostMapping("/chatrooms/{roomId}/messages")
    public String sendMessage(@PathVariable int roomId, @ModelAttribute Messages newMessage) {

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = null;
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            username = userDetails.getUsername();  // "sou" が入る
        }

        Authentication userEntity = authenticationMapper.selectByUsername(username);

        newMessage.setChat_room_id(roomId);
        newMessage.setUser(userEntity);
        messageService.sendMessage(newMessage);
        String discordWebhookUrl = System.getenv("https://discord.com/api/webhooks/1334948744810201088/uy0fHxtcYJRtnKTt9dFbTlzNAkst6mQk43dplGmf1vGcJNHMWjhJ49K8U2q2vGu57_Mw");
            if(discordWebhookUrl == null || discordWebhookUrl.isEmpty()){
                discordWebhookUrl = "https://discord.com/api/webhooks/1334948744810201088/uy0fHxtcYJRtnKTt9dFbTlzNAkst6mQk43dplGmf1vGcJNHMWjhJ49K8U2q2vGu57_Mw"; // Discord Webhook URL
                System.err.println("DISCORD_WEBHOOK_URL is not set in environment variables. Using default value. This is NOT recommended for production.");
            }

        String message = newMessage.getMessage();
            if(message != null && !message.isEmpty()){
                discordMessenger.sendDiscordMessage(discordWebhookUrl, message);
            }

        return "redirect:/chatrooms/" + roomId;


    }
}

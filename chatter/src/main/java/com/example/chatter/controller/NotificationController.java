package com.example.chatter.controller;

import com.example.chatter.dto.NotificationSettingDto;
import com.example.chatter.dto.NotificationSettingRequest;
import com.example.chatter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Object> fetchUnread(@RequestParam String username, @RequestParam int limit) {
        return notificationService.fetchUnread(username, limit);
    }

    @PutMapping("/rooms/{roomId}/readAt")
    public void markAsRead(@PathVariable int roomId, @RequestParam String username, @RequestParam LocalDateTime timestamp) {
        notificationService.markAsRead(username, roomId, timestamp);
    }

    @PutMapping("/notification-settings")
    public void saveSettings(@RequestParam String username, @RequestBody List<NotificationSettingRequest> request) {
        notificationService.saveSettings(username, request);
    }

    @GetMapping("/notification-settings")
    public List<NotificationSettingDto> getSettings(Authentication authentication) {
        String username = authentication.getName();
        return notificationService.getSettings(username);
    }
}

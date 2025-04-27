package com.example.chatter.service;

import com.example.chatter.dto.NotificationSettingDto;
import com.example.chatter.dto.NotificationSettingRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    List<Object> fetchUnread(String username, int limit);
    void markAsRead(String username, int roomId, LocalDateTime timestamp);
    void markAllAsRead(String username);
    void saveSettings(String username, List<NotificationSettingRequest> request);
    List<NotificationSettingDto> getSettings(String username);
}
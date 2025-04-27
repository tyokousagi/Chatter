package com.example.chatter.entity;

import lombok.Data;

@Data
public class NotificationSetting {
    private NotificationSettingId id;
    private boolean enabled;

    @Data
    public static class NotificationSettingId {
        private String username;
        private int chatRoomId;
    }
}
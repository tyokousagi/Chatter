package com.example.chatter.dto;

import lombok.Data;

@Data
public class NotificationSettingDto {
    private int roomId;
    private boolean enabled;
}
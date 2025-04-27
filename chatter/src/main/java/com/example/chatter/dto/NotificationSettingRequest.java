// File: chatter/src/main/java/com/example/chatter/dto/NotificationSettingRequest.java
package com.example.chatter.dto;

import lombok.Data;

/**
 * 通知設定を保存するためのリクエストDTO
 */
@Data
public class NotificationSettingRequest {
    private int roomId;
    private boolean enabled;
}

package com.example.chatter.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notification {
    // このクラスはテスト実行のために最小限の実装を残しています
    private String user_id;
    private Integer message_id;
    private Integer chat_room_id;
    private String content;
    private Boolean is_read;
    private LocalDateTime created_at;
}

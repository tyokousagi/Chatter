package com.example.chatter.entity;

import lombok.Data;

@Data
public class PushSubscription {
    // このクラスはテスト実行のために最小限の実装を残しています
    private Long id;
    private String username;
    private String endpoint;
    private String p256dh;
    private String auth;
    
    public String getEndpoint() {
        return endpoint != null ? endpoint : "";
    }
    
    public String getP256dh() {
        return p256dh != null ? p256dh : "";
    }
    
    public String getAuth() {
        return auth != null ? auth : "";
    }
    
    public String getUsername() {
        return username != null ? username : "";
    }
}

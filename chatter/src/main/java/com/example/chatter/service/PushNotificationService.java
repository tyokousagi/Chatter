package com.example.chatter.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.chatter.entity.PushSubscription;

@Service
public class PushNotificationService {
    // このクラスはテスト実行のために最小限の実装を残しています
    
    public void sendNotification(PushSubscription subscription, Map<String, Object> notificationData) {
        // このメソッドは通知機能削除の一環として実装が削除されました
        System.out.println("プッシュ通知機能は削除されました");
    }
}

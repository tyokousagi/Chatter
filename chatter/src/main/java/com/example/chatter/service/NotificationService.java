package com.example.chatter.service;

import com.example.chatter.entity.Notification;

// このインターフェースはテスト実行のために最小限の実装を残しています
public interface NotificationService {
    // テスト時に呼び出される可能性のあるメソッド
    default void createNotification(Notification notification) {
        // このメソッドは通知機能削除の一環として実装が削除されました
        System.out.println("通知機能は削除されました");
    }
}

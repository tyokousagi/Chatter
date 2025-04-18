package com.example.chatter.repository;

import org.apache.ibatis.annotations.Mapper;
import com.example.chatter.entity.Notification;

@Mapper
public interface NotificationMapper {
    // このインターフェースはテスト実行のために最小限の実装を残しています
    
    default void insert(Notification notification) {
        // 何も行わない実装
    }
}

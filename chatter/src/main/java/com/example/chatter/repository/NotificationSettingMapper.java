package com.example.chatter.repository;

import com.example.chatter.entity.NotificationSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationSettingMapper {
    List<NotificationSetting> findByUser(String username);
    void bulkUpsert(List<NotificationSetting> settings);
}
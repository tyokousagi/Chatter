package com.example.chatter.service.impl;

import com.example.chatter.dto.NotificationSettingDto;
import com.example.chatter.dto.NotificationSettingRequest;
import com.example.chatter.entity.NotificationSetting;
import com.example.chatter.entity.UserReadState;
import com.example.chatter.repository.NotificationSettingMapper;
import com.example.chatter.repository.UserReadStateMapper;
import com.example.chatter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserReadStateMapper userReadStateMapper;
    private final NotificationSettingMapper notificationSettingMapper;

    @Override
    public List<Object> fetchUnread(String username, int limit) {
        return List.of(
            Map.of("roomId", 1, "lastReadAt", LocalDateTime.now().minusHours(5).toString(), "unreadCount", 3),
            Map.of("roomId", 2, "lastReadAt", LocalDateTime.now().minusDays(1).toString(), "unreadCount", 5)
        );
    }

    @Override
    public void markAsRead(String username, int roomId, LocalDateTime timestamp) {
        UserReadState userReadState = new UserReadState();
        UserReadState.UserReadStateId id = new UserReadState.UserReadStateId();
        id.setUsername(username);
        id.setChatRoomId(roomId);
        userReadState.setId(id);
        userReadState.setLastReadAt(timestamp);
        userReadStateMapper.updateReadAt(userReadState);
    }

    @Override
    public void markAllAsRead(String username) {
        List<UserReadState> unreadStates = userReadStateMapper.selectUnread(username);
        LocalDateTime now = LocalDateTime.now();
        List<UserReadState> updatedStates = unreadStates.stream()
            .peek(state -> state.setLastReadAt(now))
            .collect(Collectors.toList());
        userReadStateMapper.bulkUpdateReadAt(updatedStates);
    }

    @Override
    public void saveSettings(String username, List<NotificationSettingRequest> request) {
        List<NotificationSetting> settings = request.stream()
            .map(req -> {
                NotificationSetting setting = new NotificationSetting();
                UserReadState.UserReadStateId id = new UserReadState.UserReadStateId();
                id.setUsername(username);
                id.setChatRoomId(req.getRoomId());
                setting.setId(id);
                setting.setEnabled(req.isEnabled());
                return setting;
            })
            .collect(Collectors.toList());
        notificationSettingMapper.bulkUpsert(settings);
    }

    @Override
    public List<NotificationSettingDto> getSettings(String username) {
        return notificationSettingMapper.findByUser(username)
            .stream()
            .map(setting -> {
                NotificationSettingDto dto = new NotificationSettingDto();
                dto.setRoomId(setting.getId().getChatRoomId());
                dto.setEnabled(setting.isEnabled());
                return dto;
            })
            .collect(Collectors.toList());
    }
}
package com.example.chatter.service;

import java.util.List;

import com.example.chatter.entity.ChatRooms;

public interface ChatRoomService {
    List<ChatRooms> findAllChatRoom();

    void createChatRoom(ChatRooms chatRooms);

    void deleteChatRoom(int id);

    ChatRooms findById(int id);
}

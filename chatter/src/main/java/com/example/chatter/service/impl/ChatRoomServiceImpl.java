package com.example.chatter.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chatter.entity.ChatRooms;
import com.example.chatter.repository.ChatRoomsMapper;
import com.example.chatter.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomsMapper chatRoomsMapper;

    @Override
    public List<ChatRooms> findAllChatRoom() {
        return chatRoomsMapper.selectAll();
    }

    @Override
    public void createChatRoom(ChatRooms chatRooms) {
        chatRooms.setCreated_at(LocalDateTime.now());
        chatRoomsMapper.create(chatRooms);
    }

    @Override
    public void deleteChatRoom(int id) {
        chatRoomsMapper.delete(id);
    }

    @Override
    public ChatRooms findById(int id) {
        return chatRoomsMapper.findById(id);
    }
    
}

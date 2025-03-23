package com.example.chatter.service;

import java.util.List;

import com.example.chatter.entity.Messages;

public interface MessageService {
    List<Messages> findAllMessage();

    Messages findByIdMessage(int id);

    void sendMessage(Messages messages);

    void updateMessage(Messages messages);

    void deleteMessage(int id);

    List<Messages> findMessagesByRoomId(int roomId);

    List<Messages> getMessagesByChatRoomId(int chatRoomId);

    
} 

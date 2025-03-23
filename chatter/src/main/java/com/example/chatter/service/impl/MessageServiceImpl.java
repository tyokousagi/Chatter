package com.example.chatter.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chatter.entity.Messages;
import com.example.chatter.repository.MessagesMapper;
import com.example.chatter.service.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessagesMapper messagesMapper;

    @Override
    public List<Messages> findAllMessage() {
        return messagesMapper.selectAll();
    }

    @Override
    public Messages findByIdMessage(int id) {
        return messagesMapper.selectById(id);
    }

    @Override
    public void sendMessage(Messages messages) {
        messagesMapper.insert(messages);
    }

    @Override
    public void updateMessage(Messages messages) {
        messagesMapper.update(messages);
    }

    @Override
    public void deleteMessage(int id) {
        messagesMapper.delete(id);
    }

    @Override
    public List<Messages> findMessagesByRoomId(int roomId) {
        return messagesMapper.getMessagesByChatRoomId(roomId);
    }

    @Override
    public List<Messages> getMessagesByChatRoomId(int chatRoomId) {
        return messagesMapper.getMessagesByChatRoomId(chatRoomId);
    }
}

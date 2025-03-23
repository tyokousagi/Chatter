package com.example.chatter.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.chatter.entity.Messages;
import java.util.List;

@Mapper
public interface MessagesMapper {
    
    List<Messages> selectAll();

    Messages selectById(int messageId);

    List<Messages> getMessagesByChatRoomId(int chatRoomId);

    void update(Messages message);

    void delete(int messageId);

    void insert(Messages message);

    
}

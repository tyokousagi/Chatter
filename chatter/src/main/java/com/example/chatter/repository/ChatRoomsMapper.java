package com.example.chatter.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.chatter.entity.ChatRooms;

@Mapper
public interface ChatRoomsMapper {
    List<ChatRooms> selectAll();

    void create(ChatRooms chatRoom);

    void delete(int id);

    ChatRooms findById(int id);
}



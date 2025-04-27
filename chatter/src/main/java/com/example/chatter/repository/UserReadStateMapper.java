package com.example.chatter.repository;

import com.example.chatter.entity.UserReadState;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserReadStateMapper {
    List<UserReadState> selectUnread(String username);
    void updateReadAt(UserReadState userReadState);
    void bulkUpdateReadAt(List<UserReadState> userReadStates);
}
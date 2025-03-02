package com.example.chatter.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.chatter.entity.Authentication;

@Mapper
public interface AuthenticationMapper {

    Authentication selectByUsername(String username);
}

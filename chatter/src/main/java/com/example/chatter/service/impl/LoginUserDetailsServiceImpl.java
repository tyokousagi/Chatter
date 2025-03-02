package com.example.chatter.service.impl;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.chatter.entity.Authentication;
import com.example.chatter.entity.LoginUser;
import com.example.chatter.repository.AuthenticationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService{

    private final AuthenticationMapper authentiationMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Authentication authentication = authentiationMapper.selectByUsername(username);

        if(authentication != null) {
            System.out.println(authentication.getUsername());
            System.out.println(authentication.getPassword());
            return new LoginUser(authentication.getUsername(), authentication.getPassword(), Collections.emptyList());
        }else {
            throw new UsernameNotFoundException(username + " => 指定しているユーザー名は存在しません");
        }
    }

}   

package com.example.chatter.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User{

    private String iconUrl;

    public LoginUser(String username, String password,Collection<? extends GrantedAuthority> authorities,String iconUrl) {
        super(username, password, authorities);
    }

    public String getIconUrl() {
        return iconUrl;
    }


}

package com.example.chatter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "authentications")
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    @Id
    private String username;
    private String password;
    private String iconUrl;
}

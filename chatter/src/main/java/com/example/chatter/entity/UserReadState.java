package com.example.chatter.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_read_state")
public class UserReadState implements Serializable {

    @Embeddable
    @Data
    public static class UserReadStateId implements Serializable {
        private String username;
        private int chatRoomId;
    }

    @EmbeddedId
    private UserReadStateId id;

    @Column(name = "last_read_at", nullable = false)
    private LocalDateTime lastReadAt;
}
package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.enums.FriendRequestStatus;
import com.example.Blog_Application2.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class FriendshipRes {



    private UserRes User;

    private FriendRequestStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public UserRes getUser() {
        return User;
    }

    public void setUser(UserRes user) {
        User = user;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

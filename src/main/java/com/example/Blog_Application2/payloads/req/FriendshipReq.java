package com.example.Blog_Application2.payloads.req;

import com.example.Blog_Application2.enums.FriendRequestStatus;
import com.example.Blog_Application2.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


public class FriendshipReq {


    private User sender;

    private User receiver;

    private FriendRequestStatus status;


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}

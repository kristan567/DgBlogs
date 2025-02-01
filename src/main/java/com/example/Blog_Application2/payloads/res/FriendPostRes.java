package com.example.Blog_Application2.payloads.res;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FriendPostRes {

    private String receiverEmail;
    private String receiverPostTitle;
    private String receiverPostContent;
    private Long receiverId;

    public FriendPostRes( Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverPostTitle() {
        return receiverPostTitle;
    }

    public void setReceiverPostTitle(String receiverPostTitle) {
        this.receiverPostTitle = receiverPostTitle;
    }

    public String getReceiverPostContent() {
        return receiverPostContent;
    }

    public void setReceiverPostContent(String receiverPostContent) {
        this.receiverPostContent = receiverPostContent;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}

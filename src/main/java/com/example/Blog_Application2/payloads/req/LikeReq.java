package com.example.Blog_Application2.payloads.req;

import lombok.Data;

@Data
public class LikeReq {
    private int postId;
    private long userId;
    private boolean isLike;
    private boolean isDisLike;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isDisLike() {
        return isDisLike;
    }

    public void setDisLike(boolean disLike) {
        isDisLike = disLike;
    }
}
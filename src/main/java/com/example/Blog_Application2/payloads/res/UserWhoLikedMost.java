package com.example.Blog_Application2.payloads.res;

public class UserWhoLikedMost {

    private UserRes userId;

    private Long likes;


    public UserWhoLikedMost(UserRes userId, Long likes) {
        this.userId = userId;
        this.likes = likes;
    }

    public UserRes getUserId() {
        return userId;
    }

    public void setUserId(UserRes userId) {
        this.userId = userId;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}

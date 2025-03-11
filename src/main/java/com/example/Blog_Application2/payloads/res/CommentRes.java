package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CommentRes {

    private int id;

    @Column(nullable = false, length =500)
    private String Content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name = "created_At", updatable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_At;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name = "updated_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private PostRes post;

    private UserRes user;

    private boolean deletable;

    private boolean LikedByUser;

    private boolean DisLikedByUser;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PostRes getPost() {
        return post;
    }

    public void setPost(PostRes post) {
        this.post = post;
    }

    public UserRes getUser() {
        return user;
    }

    public void setUser(UserRes user) {
        this.user = user;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isLikedByUser() {
        return LikedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        LikedByUser = likedByUser;
    }

    public boolean isDisLikedByUser() {
        return DisLikedByUser;
    }

    public void setDisLikedByUser(boolean disLikedByUser) {
        DisLikedByUser = disLikedByUser;
    }
}

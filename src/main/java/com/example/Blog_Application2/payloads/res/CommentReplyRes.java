package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CommentReplyRes {

    private int id;

    @Column(nullable = false, length =500)
    private String content;

    @Column(name = "created_At", updatable=false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_At;

    @Column(name = "updated_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private Post post;

    private User user;

    private Comment comment;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}

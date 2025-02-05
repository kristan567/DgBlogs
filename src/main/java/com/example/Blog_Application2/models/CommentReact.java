package com.example.Blog_Application2.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "commentReact")
@Getter
@Setter
public class CommentReact {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "commentId", nullable = false)
    private Comment comment;

    private boolean isLike;

    private boolean isDisLike;

    @Column(name = "action_date")
    private LocalDateTime actionDate;


    public CommentReact() {
        this.actionDate = LocalDateTime.now();
    }



}

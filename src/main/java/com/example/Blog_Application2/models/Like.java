package com.example.Blog_Application2.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "likes")
@Getter
@Setter
public class Like {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable =false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    private boolean isLike;

    @Column
    private boolean isDisLike;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    public Like() {
        this.actionDate = LocalDateTime.now();
    }
}

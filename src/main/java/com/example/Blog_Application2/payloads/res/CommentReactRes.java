package com.example.Blog_Application2.payloads.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentReactRes {

    private Long id;
    private CommentRes comment;
    private boolean isLike;
    private boolean isDislike;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDateTime actionDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommentRes getComment() {
        return comment;
    }

    public void setComment(CommentRes comment) {
        this.comment = comment;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isDislike() {
        return isDislike;
    }

    public void setDislike(boolean dislike) {
        isDislike = dislike;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }
}

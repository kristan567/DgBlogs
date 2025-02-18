package com.example.Blog_Application2.payloads.res;

import lombok.Data;

@Data
public class AdminTotalInfoRes {

    private Integer totalUser;

    private Integer totalPost;

    private Integer totalLike;

    private Integer totalDisLike;

    private Long totalComment;

    private Long totalCommentReply;



    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalDisLike() {
        return totalDisLike;
    }

    public void setTotalDisLike(Integer totalDisLike) {
        this.totalDisLike = totalDisLike;
    }

    public Long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Long totalComment) {
        this.totalComment = totalComment;
    }

    public Long getTotalCommentReply() {
        return totalCommentReply;
    }

    public void setTotalCommentReply(Long totalCommentReply) {
        this.totalCommentReply = totalCommentReply;
    }
}

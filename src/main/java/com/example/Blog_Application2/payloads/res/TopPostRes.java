package com.example.Blog_Application2.payloads.res;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TopPostRes {

    private PostRes post;

    private Long likeCount;

    public TopPostRes(PostRes post, Long likeCount) {
        this.post = post;
        this.likeCount = likeCount;
    }

    public PostRes getPost() {
        return post;
    }

    public void setPost(PostRes post) {
        this.post = post;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}

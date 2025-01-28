package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LikeRes {
    private Long id;
//    private int postId;
    private PostRes post;
    private boolean isLike;
    private boolean isDislike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostRes getPost() {
        return post;
    }

    public void setPost(PostRes post) {
        this.post = post;
    }



//    public int getPostId() {
//        return postId;
//    }
//
//    public void setPostId(int postId) {
//        this.postId = postId;
//    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean getDislike() {
        return isDislike;
    }

    public void setDisLike(boolean disLike) {
        isDislike = disLike;
    }
}

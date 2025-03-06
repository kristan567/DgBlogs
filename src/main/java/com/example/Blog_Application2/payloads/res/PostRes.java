package com.example.Blog_Application2.payloads.res;


import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.CategoryReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class PostRes {

    private int postId;

    private String title;

    private String content;

    private String imageName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date addDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date updateDate;

    private CategoryRes category;

    private UserRes user;

    private boolean deletable;

//    private Set<CommentRes> comments = new HashSet<>();

    private boolean LikedByUser;

    private boolean DisLikedByUser;

    private Long viewCount;






    public CategoryRes getCategory() {
        return category;
    }

    public void setCategory(CategoryRes category) {
        this.category = category;
    }

    public UserRes getUser() {
        return user;
    }

    public void setUser(UserRes user) {
        this.user = user;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

//    public Set<CommentRes> getComments() {
//        return comments;
//    }
//
//    public void setComments(Set<CommentRes> comments) {
//        this.comments = comments;
//    }

    public void excludeContent() {
        this.content = null;
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

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

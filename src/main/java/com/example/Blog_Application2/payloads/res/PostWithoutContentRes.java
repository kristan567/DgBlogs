package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
public class PostWithoutContentRes {

    private String title;

    private String imageName;

    private Date addDate;

    private Category category;

    private UserRes user;


    public PostWithoutContentRes(String title, String imageName, Date addDate, Category category, UserRes user) {
        this.title = title;
        this.imageName = imageName;
        this.addDate = addDate;
        this.category = category;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UserRes getUser() {
        return user;
    }

    public void setUser(UserRes user) {
        this.user = user;
    }
}

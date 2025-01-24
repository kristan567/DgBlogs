package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.Post;
import lombok.Data;

@Data
public class DailyPost {

    private Long post;

    private String day;

    public DailyPost(Long post, String day) {
        this.post = post;
        this.day = day;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

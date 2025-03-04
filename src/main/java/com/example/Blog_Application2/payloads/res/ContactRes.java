package com.example.Blog_Application2.payloads.res;

import com.example.Blog_Application2.models.User;
import lombok.Data;


@Data
public class ContactRes {

    private String Topic;

    private String content;

    private UserRes user;


    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public UserRes getUser() {
        return user;
    }

    public void setUser(UserRes user) {
        this.user = user;
    }
}

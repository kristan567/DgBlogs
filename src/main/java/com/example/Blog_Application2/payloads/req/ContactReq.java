package com.example.Blog_Application2.payloads.req;

import com.example.Blog_Application2.models.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ContactReq {


    private String topic;

    private String content;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}

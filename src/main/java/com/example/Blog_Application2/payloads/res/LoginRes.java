package com.example.Blog_Application2.payloads.res;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRes {

    private String accessToken;

    public LoginRes(String jwt){
        accessToken = jwt;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.example.Blog_Application2.payloads.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassReq {

    private String oldPassword;
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

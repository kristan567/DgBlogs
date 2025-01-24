package com.example.Blog_Application2.payloads.res;

public class AddedUserInMonth {

    private Long userId;

    private String addDate;


    public AddedUserInMonth(Long userId, String addDate) {
        this.userId = userId;
        this.addDate = addDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}

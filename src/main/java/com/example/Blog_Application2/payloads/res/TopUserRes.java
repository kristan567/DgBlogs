package com.example.Blog_Application2.payloads.res;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TopUserRes {

    private UserRes UserId;

    private Long occurrence;


    public TopUserRes(UserRes userId, Long occurrence) {
        UserId = userId;
        this.occurrence = occurrence;
    }

    public UserRes getUserId() {
        return UserId;
    }

    public void setUserId(UserRes userId) {
        UserId = userId;
    }

    public Long getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Long occurrence) {
        this.occurrence = occurrence;
    }
}

package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.res.*;

import java.util.List;

public interface AdminPanel {

    Integer getAllLikesCount();

    Integer getAllDisLikeCount();

    Integer getAllUsersCount();

    Integer getAllPostCount();

    Integer getAllCommentsCount();

    Integer getAllCommentsReply();

    List<CategoryUsageRes> getMostUsedCategory();

    List<TopUserRes> getMostUser();

    List<TopPostRes> getPopularPost();

    List<DailyPost> getDailyPost();

    List<AddedUserInMonth> AddedUserInMonth();


}

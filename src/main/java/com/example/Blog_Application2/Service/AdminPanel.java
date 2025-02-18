package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminPanel {

    Integer getAllLikesCount();

    Integer getAllDisLikeCount();

    Integer getAllUsersCount();

    Integer getAllPostCount();

    Integer getAllCommentsCount();

    Integer getAllCommentsReply();

    AdminTotalInfoRes getAllCountData();

    List<CategoryUsageRes> getMostUsedCategory();

    List<TopUserRes> getMostUser();

    List<TopPostRes> getPopularPost();

    List<DailyPost> getDailyPost();

    List<AddedUserInMonth> AddedUserInMonth();

    UserRes createUser(UserReq userReq, MultipartFile image, String path);


}

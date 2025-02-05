package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.LikeRes;

import java.util.List;

public interface LikeService {

    LikeRes likeOrDislikePos(LikeReq likeReq,Integer postId);

    List<LikeRes> getPostLikes(int postId);

    List<LikeRes> getLikesByUser(int userId);

    List<LikeRes> getLikesByAuthenticatedUser();

    List<LikeRes> getLikesOnlyByAuthenticatedUser();

    List<LikeRes> getDisLikesByAuthenticatedUser();

    Integer getPostLikeCount(int postId);

    Integer getPostDisLikeCount(int postId);

    Integer getPostLikeDisLikeCount(int postId);






}

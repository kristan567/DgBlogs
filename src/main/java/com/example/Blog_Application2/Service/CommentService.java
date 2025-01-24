package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.CommentReq;
import com.example.Blog_Application2.payloads.res.CommentRes;
import com.example.Blog_Application2.payloads.res.PagePostRes;

import java.util.List;

public interface CommentService {

    //create
    CommentRes createComment(CommentReq commentReq, Integer postId);

    //update
    CommentRes updateComment(CommentReq commentReq,Integer CommentId);

    //read
    List<CommentRes> getAllComments();

    List<CommentRes> getCommentsByUser(Integer userId);

    List<CommentRes> getCommentsByUserLoggedIn();

    List<CommentRes> getCommentsByPost(Integer postId);

//    PageCommentRes getCommentByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //delete
    String deleteComment(Integer CommentId);

    public Long getCommentCountByPost(int postId);






}

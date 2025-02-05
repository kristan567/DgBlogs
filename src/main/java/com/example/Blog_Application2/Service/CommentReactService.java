package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.CommentReactReq;
import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.res.CommentReactRes;

public interface CommentReactService {


    CommentReactRes likesOrDisLikes(CommentReactReq commentReactReq, Integer commentId);

    Integer GetCommentLikeCount(int commentId);

    Integer GetCommentDisLikeCount(int commentId);




}

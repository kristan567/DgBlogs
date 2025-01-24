package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.res.CommentReplyRes;

import java.util.List;

public interface CommentReplyService {

    CommentReplyRes createCommentReplay(CommentReplyReq commentReplyReq, Integer commentId, Integer postId );

    CommentReplyRes updateCommentReply(CommentReplyReq commentReplyReq, Integer commentReplyId);

    List<CommentReplyRes> getAllCommentsReplyByComments(Integer commentId);

    String deleteCommentReply(Integer commentReplyId);

    Long getCommentReplyCountByComment(int commentId);

}

package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.CommentReactService;
import com.example.Blog_Application2.payloads.req.CommentReactReq;
import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.CommentReactRes;
import com.example.Blog_Application2.payloads.res.LikeRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("commentReact")
public class CommentReactController {

    private final CommentReactService commentReactService;

    public CommentReactController(CommentReactService commentReactService) {
        this.commentReactService = commentReactService;
    }

    @Operation(summary = "to create like or disLike in the post")
    @PostMapping("/likeOrDislike/{commentId}")
    public CommentReactRes likeOrDislikePost(@RequestBody CommentReactReq commentReactReq, @PathVariable Integer commentId) {
        return commentReactService.likesOrDisLikes(commentReactReq, commentId);
    }

    @Operation(summary = "comment like count")
    @GetMapping("/{commentId}/likeCount")
    public int getCommentLikeCount(@PathVariable int commentId) {
        return commentReactService.GetCommentLikeCount(commentId);
    }

    @Operation(summary = "comment dislike count")
    @GetMapping("/{commentId}/dislikeCount")
    public int getCommentDisLikeCount(@PathVariable int commentId) {
        return commentReactService.GetCommentDisLikeCount(commentId);
    }

}

package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.LikeService;
import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.LikeRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("like")
public class LikeController {

    @Autowired
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "to create like or disLike in the post")
    @PostMapping("/likeOrDislike/{postId}")
    public LikeRes likeOrDislikePost(@RequestBody LikeReq likeReq, @PathVariable Integer postId) {
        return likeService.likeOrDislikePos(likeReq, postId);
    }

    @Operation(summary = "view all likes in the post")
    @GetMapping("/likesInPost/{postId}")
    public List<LikeRes> getPostLikes(@PathVariable int postId) {
        return likeService.getPostLikes(postId);
    }

    @Operation(summary = "view likes by the user")
    @GetMapping("/likesFromUser/{userId}")
    public ResponseEntity<?> getLikesByUser(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikesByUser(userId));
    }

    @Operation(summary = "likes by the authenticated user")
    @GetMapping("/likesByAuthenticatedUser")
    public ResponseEntity<?> getLikesByAuthenticatedUser(){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikesByAuthenticatedUser());
    }

    @Operation(summary = "likes only by the authenticated user")
    @GetMapping("/likesOnlyByAuthenticatedUser")
    public ResponseEntity<?> getLikesOnlyByAuthenticatedUser(){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikesOnlyByAuthenticatedUser());
    }

    @Operation(summary = "dislikes only by the authenticated user")
    @GetMapping("/dislikesByAuthenticatedUser")
    public ResponseEntity<?> getDisLikesByAuthenticatedUser(){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getDisLikesByAuthenticatedUser());
    }

    @Operation(summary = "post like count")
    @GetMapping("/{postId}/likeCount")
    public int getPostLikeCount(@PathVariable int postId) {
        return likeService.getPostLikeCount(postId);
    }

    @Operation(summary = "post dislike count")
    @GetMapping("/{postId}/disLikeCount")
    public int getPostDislikeCount(@PathVariable int postId) {
        return likeService.getPostDisLikeCount(postId);
    }

    @Operation(summary = "post likes and dislike count")
    @GetMapping("/{postId}/likeAndDislikeCount")
    public int getPostLikeDislikeCount(@PathVariable int postId) {
        return likeService.getPostLikeDisLikeCount(postId);
    }

}


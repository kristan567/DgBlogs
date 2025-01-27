package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.LikeService;
import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.LikeRes;
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

    @PostMapping("/likeOrDislike/{postId}")
    public LikeRes likeOrDislikePost(@RequestBody LikeReq likeReq, @PathVariable Integer postId) {
        return likeService.likeOrDislikePos(likeReq, postId);
    }

    @GetMapping("/likesInPost/{postId}")
    public List<LikeRes> getPostLikes(@PathVariable int postId) {
        return likeService.getPostLikes(postId);
    }

    @GetMapping("/likesFromUser/{userId}")
    public ResponseEntity<?> getLikesByUser(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikesByUser(userId));
    }

    @GetMapping("/likesByAuthenticatedUser")
    public ResponseEntity<?> getLikesByAuthenticatedUser(){
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikesByAuthenticatedUser());
    }

    @GetMapping("/{postId}/likeCount")
    public int getPostLikeCount(@PathVariable int postId) {
        return likeService.getPostLikeCount(postId);
    }

    @GetMapping("/{postId}/disLikeCount")
    public int getPostDislikeCount(@PathVariable int postId) {
        return likeService.getPostDisLikeCount(postId);
    }

    @GetMapping("/{postId}/likeAndDislikeCount")
    public int getPostLikeDislikeCount(@PathVariable int postId) {
        return likeService.getPostLikeDisLikeCount(postId);
    }


}


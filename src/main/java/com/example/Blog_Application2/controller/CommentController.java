package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.CommentService;
import com.example.Blog_Application2.payloads.req.CommentReq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentReq commentReq, @PathVariable Integer postId){

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentReq, postId));
    }

    @PutMapping("/Update-comment/{CommentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentReq commentReq, @PathVariable Integer CommentId){

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentReq, CommentId));
    }

    @GetMapping("/get-comments")
    public ResponseEntity<?> getAllComments(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments());
    }

    @GetMapping("/comments-user/{userId}")
    public ResponseEntity<?> getCommentsByUser(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(userId));
    }

    @GetMapping("/comments-user")
    public ResponseEntity<?> getCommentsByUserLoggedIn(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUserLoggedIn());
    }

    @GetMapping("/comments-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }

    @DeleteMapping("/comment/{CommentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer CommentId){

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(CommentId));
    }

    @GetMapping("/{postId}/commentCount")
    public Long getCommentLikeCount(@PathVariable int postId) {
        return commentService.getCommentCountByPost(postId);
    }

}

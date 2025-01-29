package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.CommentService;
import com.example.Blog_Application2.payloads.req.CommentReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "create comment")
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentReq commentReq, @PathVariable Integer postId){

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentReq, postId));
    }

    @Operation(summary = "update comment")
    @PutMapping("/Update-comment/{CommentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentReq commentReq, @PathVariable Integer CommentId){

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentReq, CommentId));
    }

    @Operation(summary = "get all comments")
    @GetMapping("/get-comments")
    public ResponseEntity<?> getAllComments(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments());
    }

    @Operation(summary = "Get comments by User id")
    @GetMapping("/comments-user/{userId}")
    public ResponseEntity<?> getCommentsByUser(@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(userId));
    }

    @Operation(summary = "Get comments by user who is logged In")
    @GetMapping("/comments-user")
    public ResponseEntity<?> getCommentsByUserLoggedIn(){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUserLoggedIn());
    }

    @Operation(summary = "get Comments according to the post")
    @GetMapping("/comments-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }

    @Operation(summary = "delete the comment")
    @DeleteMapping("/comment/{CommentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer CommentId){

        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(CommentId));
    }

    @Operation(summary = "Count comment in the post")
    @GetMapping("/{postId}/commentCount")
    public Long getCommentLikeCount(@PathVariable int postId) {
        return commentService.getCommentCountByPost(postId);
    }

}

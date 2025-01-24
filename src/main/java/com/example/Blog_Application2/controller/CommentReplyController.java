package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.CommentReplyService;
import com.example.Blog_Application2.config.jwt.JwtUtil;
import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.req.CommentReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentReplyController {

    private final CommentReplyService commentReplyService;

    private final JwtUtil jwtUtil;

    public CommentReplyController(CommentReplyService commentReplyService, JwtUtil jwtUtil) {
        this.commentReplyService = commentReplyService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("post/{postId}/comment/{commentId}/comment-reply")
    public ResponseEntity<?> createReply(@RequestBody CommentReplyReq commentReplyReq, @PathVariable Integer commentId, @PathVariable Integer postId, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentReplyService.createCommentReplay(commentReplyReq, commentId, postId));
    }

    @PutMapping("/update-commentReply/{commentReplyId}")
    public ResponseEntity<?> updateCommentReply(@RequestBody CommentReplyReq commentReplyReq,@PathVariable Integer commentReplyId){
        return ResponseEntity.status(HttpStatus.OK).body(commentReplyService.updateCommentReply(commentReplyReq, commentReplyId));
    }

    @GetMapping("/CommentReply-comment/{commentId}")
    public ResponseEntity<?> getCommentReplyByComment(@PathVariable Integer commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentReplyService.getAllCommentsReplyByComments(commentId));
    }

    @DeleteMapping("Comment-Reply/{commentReplyId}")
    public ResponseEntity<?> deleteCommentReplay(@PathVariable Integer commentReplyId ){
        return ResponseEntity.status(HttpStatus.OK).body(commentReplyService.deleteCommentReply(commentReplyId));
    }

    @GetMapping("/{commentId}/commentReplyCount")
    public Long getCommentLikeCount(@PathVariable int commentId) {
        return commentReplyService.getCommentReplyCountByComment(commentId);
    }




}

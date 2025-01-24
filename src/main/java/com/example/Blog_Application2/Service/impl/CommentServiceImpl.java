package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.CommentService;
import com.example.Blog_Application2.Service.mappers.CommentMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.CommentReq;
import com.example.Blog_Application2.payloads.res.CommentRes;
import com.example.Blog_Application2.repository.CommentRepository;
import com.example.Blog_Application2.repository.PostRepository;
import com.example.Blog_Application2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final AuthenticationFacade authenticationFacade;


    public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, CommentMapper commentMapper, AuthenticationFacade authenticationFacade) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public CommentRes createComment(CommentReq commentReq, Integer postId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Post post = postRepository.findById(postId).orElseThrow(()->new CustomException("Post not found with Id: "+ postId, HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException("User not Found with Id: "+ userId, HttpStatus.NOT_FOUND));
        Comment comment  = commentMapper.toEntity(commentReq);
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreated_At(new Date());
        commentRepository.save(comment);

        return commentMapper.toDtoTwo(comment);
    }

    @Override
    public CommentRes updateComment(CommentReq commentReq, Integer CommentId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Comment comment = commentRepository.findById(CommentId).orElseThrow(()->new CustomException("Comment not Found with Id: " + CommentId, HttpStatus.NOT_FOUND));
        if(userId == comment.getUser().getId() ){
            comment.setContent(commentReq.getContent().trim());
            comment.setUpdatedAt(new Date());
            commentRepository.save(comment);

            return commentMapper.toDtoTwo(comment);
        }else{
            throw new CustomException("not a Valid user to update", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CommentRes> getAllComments() {
        List<Comment> commentList = this.commentRepository.findAll();
        List<CommentRes> res = new ArrayList<>();
        commentList.forEach(comment->{
            res.add(commentMapper.toDtoTwo(comment));
        });

        return res;
    }

    @Override
    public List<CommentRes> getCommentsByUser(Integer userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("user with Id: " + userId + "Not Found", HttpStatus.NOT_FOUND));
        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream()
                .map(comment->commentMapper.toDtoTwo(comment))
                .collect(Collectors.toList());

    }

    @Override
    public List<CommentRes> getCommentsByUserLoggedIn(){
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException("user not Found", HttpStatus.NOT_FOUND));

        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream()
                .map(comment->commentMapper.toDtoTwo(comment))
                .collect(Collectors.toList());
    }

    //add get comments according to the user logged in

    @Override
    public List<CommentRes> getCommentsByPost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException("post Not Found with postId" + postId, HttpStatus.NOT_FOUND));
        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment->commentMapper.toDtoTwo(comment))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteComment(Integer CommentId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Comment comment = commentRepository.findById(CommentId).orElseThrow(()->new CustomException("Comment not Found With Id: " + CommentId, HttpStatus.NOT_FOUND));
        if(userId != comment.getUser().getId()){
            throw new CustomException("User Not Valid to Delete", HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(comment);
        return "Comment Deleted";
    }

    @Override
    public Long getCommentCountByPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
        long postIdLong = post.getPostId();
        return commentRepository.countByPostId(postIdLong);
    }

}

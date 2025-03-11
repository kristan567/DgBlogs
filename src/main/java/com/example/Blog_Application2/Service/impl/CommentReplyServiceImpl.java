package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.CommentReplyService;
import com.example.Blog_Application2.Service.mappers.CommentReplyMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.CommentReply;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.res.CommentReplyRes;
import com.example.Blog_Application2.payloads.res.CommentRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import com.example.Blog_Application2.repository.CommentReplyRepository;
import com.example.Blog_Application2.repository.CommentRepository;
import com.example.Blog_Application2.repository.PostRepository;
import com.example.Blog_Application2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentReplyServiceImpl implements CommentReplyService {

    private final CommentReplyRepository commentReplyRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentReplyMapper commentReplyMapper;

    private final AuthenticationFacade authenticationFacade;


    public CommentReplyServiceImpl(CommentReplyRepository commentReplyRepository, CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentReplyMapper commentReplyMapper, AuthenticationFacade authenticationFacade) {
        this.commentReplyRepository = commentReplyRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentReplyMapper = commentReplyMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public CommentReplyRes createCommentReplay(CommentReplyReq commentReplyReq, Integer commentId, Integer postId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException("Comment not Found with Id: "+ commentId, HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(()->new CustomException("Post not found with Id: "+ postId, HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException("user not found: ", HttpStatus.NOT_FOUND));
        CommentReply commentReply = commentReplyMapper.toEntity(commentReplyReq);
        commentReply.setComment(comment);
        commentReply.setPost(post);
        commentReply.setUser(user);
        commentReply.setCreated_At(new Date());

        commentReplyRepository.save(commentReply);
        return commentReplyMapper.toDtoTwo(commentReply);

    }

    @Override
    public CommentReplyRes updateCommentReply(CommentReplyReq commentReplyReq, Integer commentReplyId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(()->new CustomException("commentReply Not Found with Id: "+ commentReplyId, HttpStatus.NOT_FOUND));
        if(userId != commentReply.getUser().getId())
            throw new CustomException("invalid User", HttpStatus.BAD_REQUEST);
        commentReply.setContent(commentReplyReq.getContent().trim());
        commentReply.setUpdatedAt(new Date());
        commentReplyRepository.save(commentReply);


        return commentReplyMapper.toDtoTwo(commentReply);
    }

    @Override
    public List<CommentReplyRes> getAllCommentsReplyByComments(Integer commentId) {

        Long userId = null;

        try {
            userId = authenticationFacade.getAuthentication().getUserId();
        } catch (Exception e) {
            userId = null;
        }
        if(userId != null){

            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("comment not found", HttpStatus.NOT_FOUND));
            List<CommentReply> commentReply = commentReplyRepository.findByComment(comment);
            List<CommentReplyRes> res = new ArrayList<>();

            Integer commentReplyId = commentReply.isEmpty() ? null : commentReply.get(0).getId();
            Optional<CommentReply> commentReply1 = commentReplyRepository.findById(commentReplyId);

            CommentReply commentReply2 = commentReply1.orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));


            Long finalUserId = userId;

            commentReply.forEach(commentReply3 -> {
                CommentReplyRes commentReplyRes = commentReplyMapper.toDtoTwo(commentReply3);
                if(finalUserId.equals(commentReply2.getUser().getId())){
                    commentReplyRes.setDeletable(true);
                }else{
                    commentReplyRes.setDeletable(false);
                }

                res.add(commentReplyRes);
            });

            return res;

        }else {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("comment not found", HttpStatus.NOT_FOUND));
            List<CommentReply> commentReply = commentReplyRepository.findByComment(comment);
            return commentReply.stream()
                    .map(commentReplies -> commentReplyMapper.toDtoTwo(commentReplies))
                    .collect(Collectors.toList());
        }
    }

    public CommentReplyRes getCommentReplyById(Integer commentReplyId){


        Long userId = null;

        try {
            userId = authenticationFacade.getAuthentication().getUserId();
        } catch (Exception e) {
            userId = null;
        }
        if(userId != null){


            Optional<CommentReply> commentReply = commentReplyRepository.findById(commentReplyId);
            CommentReply commentReply1 = commentReply.orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
            List<CommentReplyRes> res = new ArrayList<>();

            Long finalUserId = userId;

                CommentReplyRes commentReplyRes = commentReplyMapper.toDtoTwo(commentReply1);
                if(finalUserId.equals(commentReply1.getUser().getId())){
                    commentReplyRes.setDeletable(true);
                }else{
                    commentReplyRes.setDeletable(false);
                }
            return commentReplyRes;

        }else {
            Optional<CommentReply> commentReply = commentReplyRepository.findById(commentReplyId);
            if (commentReply.isEmpty())
                throw new CustomException("post with Id: " + commentReplyId + " not found", HttpStatus.NOT_FOUND);

            CommentReply commentReplyEntity = commentReply.orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));




            CommentReplyRes res = commentReplyMapper.toDtoTwo(commentReply.get());
//        PostRes res = TransferObject.convert(post.get(), PostRes.class);
            return res;
        }

    }

    @Override
    public String deleteCommentReply(Integer commentReplyId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        CommentReply commentReply = commentReplyRepository.findById(commentReplyId).orElseThrow(()-> new CustomException("comment not found with Id: "+ commentReplyId, HttpStatus.NOT_FOUND));
        if(userId ==commentReply.getUser().getId()){
            commentReplyRepository.delete(commentReply);

            return "Comment deleted";
        }else{
            throw new CustomException("User do not have prior role", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Long getCommentReplyCountByComment(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
        long commentReplyIdLong = comment.getId();
        return commentReplyRepository.countByCommentId(commentReplyIdLong);
    }

}

package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.CommentReactService;
import com.example.Blog_Application2.Service.mappers.CommentMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.CommentReact;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.CommentReactReq;
import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.res.CommentReactRes;
import com.example.Blog_Application2.payloads.res.CommentRes;
import com.example.Blog_Application2.repository.CommentReactRepository;
import com.example.Blog_Application2.repository.CommentRepository;
import com.example.Blog_Application2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentReactServiceImpl implements CommentReactService {

    private final AuthenticationFacade authenticationFacade;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final CommentMapper commentMapper;

    private final CommentReactRepository commentReactRepository;


    public CommentReactServiceImpl(AuthenticationFacade authenticationFacade, CommentRepository commentRepository, UserRepository userRepository, CommentMapper commentMapper, CommentReactRepository commentReactRepository) {
        this.authenticationFacade = authenticationFacade;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.commentReactRepository = commentReactRepository;
    }

    @Override
    public CommentReactRes likesOrDisLikes(CommentReactReq commentReactReq, Integer commentId) {

        Long userId = authenticationFacade.getAuthentication().getUserId();
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CustomException("comment not Found", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException("comment not Found", HttpStatus.NOT_FOUND));


        CommentRes commentData = commentMapper.toDtoTwo(comment);

        List<CommentReact> existingCommentReactList = commentReactRepository.findByUserAndComment(user, comment);

        CommentReact commentReact;

        if(!existingCommentReactList.isEmpty()) {
            commentReact = existingCommentReactList.get(0);
            commentReact.setLike(commentReactReq.isLike());
            commentReact.setDisLike(commentReactReq.isDislike());
        }else{
            commentReact = new CommentReact();
            commentReact.setUser(user);
            commentReact.setComment(comment);
            commentReact.setLike(commentReactReq.isLike());
            commentReact.setDisLike(commentReactReq.isDislike());
            commentReact.setActionDate(LocalDateTime.now());
        }

        commentReact = commentReactRepository.save(commentReact);

        CommentReactRes commentReactRes = new CommentReactRes();
        commentReactRes.setId((long) commentReact.getId());
        commentReactRes.setComment(commentData);
        commentReactRes.setLike(commentReact.isLike());
        commentReactRes.setDislike(commentReact.isDisLike());

        return commentReactRes;
    }

    @Override
    public Integer GetCommentLikeCount(int commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException("comment not found", HttpStatus.NOT_FOUND));

        Long commentIdLong = (long) comment.getId();

        return commentReactRepository.countByCommentAndIsLike(commentIdLong, true);
    }

    @Override
    public Integer GetCommentDisLikeCount(int commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException("comment not found", HttpStatus.NOT_FOUND));

        Long commentIdLong = (long) comment.getId();

        return commentReactRepository.countByCommentAndIsDisLike(commentIdLong, true);
    }
}

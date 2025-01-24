package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.LikeService;
import com.example.Blog_Application2.Service.mappers.LikeMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Like;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.LikeRes;
import com.example.Blog_Application2.repository.LikeRepository;
import com.example.Blog_Application2.repository.PostRepository;
import com.example.Blog_Application2.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final AuthenticationFacade authenticationFacade;

    private final LikeMapper likeMapper;

    public LikeServiceImpl(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, AuthenticationFacade authenticationFacade, LikeMapper likeMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.authenticationFacade = authenticationFacade;
        this.likeMapper = likeMapper;
    }

    @Override
    public LikeRes likeOrDislikePos(LikeReq likeReq, Integer postId)
    {
        Long userId = authenticationFacade.getAuthentication().getUserId();       
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        List<Like> existingLikes = likeRepository.findByUserAndPost(user, post);
        Like like;

        if (!existingLikes.isEmpty()) {
            like = existingLikes.get(0);
            like.setLike(likeReq.isLike());
            like.setDisLike(likeReq.isDisLike());
        } else {
            like = new Like();
            like.setUser(user);
            like.setPost(post);
            like.setLike(likeReq.isLike());
            like.setDisLike(likeReq.isDisLike());
        }
        like = likeRepository.save(like);
        LikeRes likeRes = new LikeRes();
        likeRes.setId(like.getId());
        likeRes.setPostId(post.getPostId());
        likeRes.setLike(like.isLike());
        likeRes.setDisLike(like.isDisLike());


        return likeRes;
    }

    @Override
    public List<LikeRes> getPostLikes(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
//        return likeRepository.findByPost(post).stream()
//                .filter(Like::isLike)
//                .map(like -> {
//                    LikeRes likeRes = new LikeRes();
//                    likeRes.setId(like.getId());
//
//                    likeRes.setPostId(post.getPostId());
//                    likeRes.setLike(like.isLike());
//
//                    return likeRes;
//                })
//                .collect(Collectors.toList());

        List<Like> likes = likeRepository.findByPost(post);

        return likes.stream()
                .map(like->likeMapper.toDtoTwo(like))
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeRes> getLikesByUser(int userId) {
        User user = userRepository.findById((long) userId).orElseThrow(()->new CustomException("user with Id: " + userId + "Not Found", HttpStatus.NOT_FOUND));

        List<Like> likes = likeRepository.findByUser(user);

        return likes.stream()
                .map(like->likeMapper.toDtoTwo(like))
                .collect(Collectors.toList());

    }

    @Override
    public List<LikeRes> getLikesByAuthenticatedUser() {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException("user with Id: " + userId + "Not Found", HttpStatus.NOT_FOUND));

        List<Like> likes = likeRepository.findByUser(user);

        return likes.stream()
                .map(like->likeMapper.toDtoTwo(like))
                .collect(Collectors.toList());

    }

    @Override
    public Integer getPostLikeCount(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
        long postIdLong = post.getPostId();
        return (int) likeRepository.countByPostAndIsLike(postIdLong, true);
    }

    @Override
    public Integer getPostDisLikeCount(int postId) {
        // Check if the post exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

        // Count the number of dislikes for the post
        long postIdLong = post.getPostId();
        return (int) likeRepository.countByPostAndIsDisLike(postIdLong, true);
    }

    @Override
    public Integer getPostLikeDisLikeCount(int postId) {
        // Check if the post exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

        // Count the number of dislikes for the post
        long postIdLong = post.getPostId();
        return (int) likeRepository.countByPostIsLikeAndIsDisLike(postIdLong, true, true);
    }




}

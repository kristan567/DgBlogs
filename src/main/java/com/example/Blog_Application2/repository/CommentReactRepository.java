package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.Service.mappers.LikeMapper;
import com.example.Blog_Application2.models.*;
import com.example.Blog_Application2.payloads.req.CommentReactReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentReactRepository extends JpaRepository<CommentReact, Integer > {


    List<CommentReact> findByUserAndComment(User user, Comment comment);

    @Query(value = "SELECT COUNT(c.id) FROM comment_react c WHERE c.comment_id = :commentId AND c.is_like = :is_Like", nativeQuery = true)
    int countByCommentAndIsLike(@Param("commentId") long commentId, @Param("is_Like") boolean isLike);


    @Query(value = "SELECT COUNT(c.id) FROM comment_react c WHERE c.comment_id = :commentId AND c.is_dis_like = :is_dislike", nativeQuery = true)
    int countByCommentAndIsDisLike(@Param("commentId") long commentId, @Param("is_dislike") boolean isDisLike);

}

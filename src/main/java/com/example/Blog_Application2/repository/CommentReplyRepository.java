package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.CommentReply;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CommentReplyRepository extends JpaRepository<CommentReply, Integer> {

    List<CommentReply> findByComment(Comment comment);

    @Query(value = "SELECT COUNT(c.id) from comment_reply c where c.comment_id = :comment_id", nativeQuery = true)
    Long countByCommentId(@Param("comment_id") long commentId);

}

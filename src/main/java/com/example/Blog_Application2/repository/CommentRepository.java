package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

    @Query(value = "SELECT COUNT(c.id) from comments c where c.post_id = :post_id", nativeQuery = true)
    Long countByPostId(@Param("post_id") long postId);


}

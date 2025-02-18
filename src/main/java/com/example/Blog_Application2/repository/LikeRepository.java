package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.Like;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {

    List<Like> findByPost(Post post);

    @Query(value = "Select * from likes  where post_id = :postId and is_Like = 1", nativeQuery = true)   //this query are for getting a flag if the user has liked the post or not
    List<Like> findPostWithLikeOnly(Integer postId);

    @Query(value = "Select * from likes  where post_id = :postId and is_dis_Like = 1", nativeQuery = true) //this query are for getting a flag if the user has disliked the post or not
    List<Like> findPostWithDislikeOnly(Integer postId);

    List<Like> findByUser(User user);

    List<Like> findByUserAndPost(User user, Post post);

    @Query(value = "Select * from likes  where is_Like = 1", nativeQuery = true)
    List<Like> findUserlike(User user);

    @Query(value = "Select * from likes  where is_dis_Like = 1", nativeQuery = true)
    List<Like> findUserDislike(User user);

    @Query(value = "SELECT COUNT(l.id) FROM likes l WHERE l.post_id = :postId AND l.is_like = :is_Like", nativeQuery = true)
    int countByPostAndIsLike(@Param("postId") long postId, @Param("is_Like") boolean isLike);

    @Query(value = "SELECT COUNT(l.id) FROM likes l WHERE l.post_id = :postId AND l.is_dis_like = :is_disLike", nativeQuery = true)
    int countByPostAndIsDisLike(@Param("postId") long postId, @Param("is_disLike")boolean isDisLike);

    @Query(value = "SELECT COUNT(l.id) FROM likes l WHERE l.post_id = :postId AND l.is_like = :is_like AND l.is_dis_like = :is_disLike", nativeQuery = true)
    int countByPostIsLikeAndIsDisLike(@Param("postId") long postId, @Param("is_Like") boolean isLike, @Param("is_disLike")boolean isDisLike);

    @Query(value = "SELECT COUNT(l.id) FROM likes l WHERE l.is_Like = 1", nativeQuery = true)
    int countToAlLikes(@Param("is_Like") boolean isLike);

    @Query(value = "SELECT COUNT(l.id) FROM likes l WHERE l.is_dis_Like = 1", nativeQuery = true)
    int countToAlDisLikes(@Param("is_disLike") boolean isDisLike);

    @Query("SELECT l.post, count(l) AS likeCount FROM Like l WHERE l.isLike = true GROUP BY l.post ORDER BY likeCount DESC")
    List<Object[]> findTopPost();  //worked after making it true instead of 1



}

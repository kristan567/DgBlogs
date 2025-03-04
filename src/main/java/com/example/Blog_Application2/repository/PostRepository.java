package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Blog_Application2.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key")String title);

    @Query("Select count(P.id) from Post P")
    Integer countTotalPosts();

    @Query("Select p.title, p.addDate, p.imageName, p.category, p.user from Post p")
    List<Object[]> getAllPostsWithoutContent();


    @Query("SELECT p.category, COUNT(p) AS occurrence FROM Post p GROUP BY p.category ORDER BY occurrence DESC")
    List<Object[]> findCategoryUsage();


    @Query("SELECT p.user, count(p) AS occurrence from Post p GROUP BY p.user ORDER BY occurrence DESC")
    List<Object[]> findTopUser();

    @Query(value = "SELECT DATE_FORMAT(p.addDate, '%Y-%m-%d') AS day, COUNT(p) AS totalPost FROM Post p GROUP BY day")
    List<Object[]> postCountByDay();

    @Query("SELECT COUNT(p) FROM Post p WHERE p.user.id = :userId AND FUNCTION('DATE', p.addDate) = CURRENT_DATE")
    int countPostsByUserToday(@Param("userId") Long userId);


//    @Query("SELECT p FROM Post p WHERE p.user IN :friendIds")
//    List<Post> findAllByUserIdIn(@Param("friendIds") List<Long> friendIds);


    List<Post> findByUserIn(List<User> users);

    @Query("select p from Post p order by viewCount desc limit 2")
    List<Post> topViewPost ();
}

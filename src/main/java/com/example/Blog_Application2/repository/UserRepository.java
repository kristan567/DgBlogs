package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.LoginReq;
import com.example.Blog_Application2.projection.UserProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "Select * from users u where u.email= ?1", nativeQuery = true)
    Optional<User> findByUsername(String userName);


    @Query(value = "select u.id,u.first_name,u.middle_name,u.last_name,u.email,u.phone,u.is_active, u.password, u.role from users u where u.email= ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

//    @Query("")
//    boolean updateTheEmail(String email);

    @Transactional
    @Modifying //says it is modified
    @Query("update User u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);


    @Query("Select count(U.id) from User U")
    Integer countTotalUsers();

    @Query(value="SELECT DATE_FORMAT(u.add_date, '%y-%m-%d') AS month, COUNT(id) AS userCount FROM users u GROUP BY month", nativeQuery = true)
//    @Query(value ="SELECT DATE_FORMAT(p.addDate, '%Y-%m-%d') AS day, COUNT(p) AS totalPost FROM Post p GROUP BY day")
    List<Object[]> UserIncreasedInMonth();


    @Query(value="SELECT * from users u where u.email = ?1", nativeQuery = true)
    User findUser(String Username);





    @Query(value = "SELECT * FROM users u WHERE u.id = :id", nativeQuery = true)
    Optional<User> findForEmail(@Param("id") Long id);


}

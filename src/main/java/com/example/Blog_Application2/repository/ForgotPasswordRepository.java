package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.ForgotPassword;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findOtpAndUser(Integer otp, User user);

    @Query("select fp from ForgotPassword fp where fp.user = user")
    Optional<ForgotPassword> findByUserId(User user);



}

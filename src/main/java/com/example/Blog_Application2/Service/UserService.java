package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.ResetPassReq;
import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.PageUserRes;
import com.example.Blog_Application2.payloads.res.UserRes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {

    UserRes save(UserReq userReq);

    UserRes createUserWithImage(UserReq userReq, MultipartFile image, String path);

    UserRes createUser(UserReq userReq);

    UserRes findById(long id);

    List<UserRes> getAllUsers();

    String deleteById();

    UserRes updateById( UserReq userReq);

    UserRes updateUserImage(MultipartFile image, String path);

    UserRes findByEmail(String email);

    String resetPassword(ResetPassReq passReq);

    public PageUserRes getUserByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir );

    public UserRes findByUserAuthentication();


}

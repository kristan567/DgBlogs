package com.example.Blog_Application2.controller;


import com.example.Blog_Application2.Service.impl.AdminPanelImpl;
import com.example.Blog_Application2.payloads.req.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Blog_Application2.Service.AdminPanel;
import com.example.Blog_Application2.payloads.res.AdminTotalInfoRes;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")

public class AdminController {


    private final AdminPanel adminPanel;

    @Value("${project.image}")
    private String path;


    public AdminController(AdminPanel adminPanel) {

        this.adminPanel = adminPanel;

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all total users")
    @GetMapping("/getAllUsers")
    public ResponseEntity<Integer> TotalUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllUsersCount());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get total likes counts")
    @GetMapping("/getAllLikes")
    public ResponseEntity<?> totalLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllLikesCount());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all dislike counts")
    @GetMapping("/getAllDisLikes")
    public ResponseEntity<?> totalDisLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllDisLikeCount());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all total post")
    @GetMapping("/getAllPost")
    public ResponseEntity<?> totalPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllPostCount());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get all comments counts")
    @GetMapping("/getAllComments")
    public ResponseEntity<?> Comments(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsCount());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get comments reply count")
    @GetMapping("/getAllCommentsReply")
    public ResponseEntity<?> getAllCommentsReply(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsReply());
    }

    @Operation(summary = "get all count data")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllCountData")
    public ResponseEntity<AdminTotalInfoRes> getAllCountData(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCountData());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get most used category")
    @GetMapping("/mostUsedCategory")
    public ResponseEntity<?> getMostUsedCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUsedCategory());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get top users ")
    @GetMapping("/topUser")
    public ResponseEntity<?> getTopUser(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUser());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get user who liked most")
    @GetMapping("/topUserWhoLikedMost")
    public ResponseEntity<?> getUserWhoLikedMost(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getUserWhoLikedMost());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get top popular most")
    @GetMapping("/topPost")
    public ResponseEntity<?> getTopPost(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getPopularPost());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get post increase or decrease in a day")
    @GetMapping("/postByMonth")
    public ResponseEntity<?> postByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getDailyPost());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "user increase/decrease in a month")
    @GetMapping("/userByMonth")
    public ResponseEntity<?> userByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.AddedUserInMonth());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "this is for creating admin with image")
    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@ModelAttribute UserReq userReq, @RequestParam("image") MultipartFile image){

        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.createUser(userReq, image, path));
    }





}

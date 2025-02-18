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

    @Autowired
    private AdminPanel adminPanel;

    @Value("${project.image}")
    private String path;


    





//    public AdminController(AdminPanel adminPanel) {
//
//        this.adminPanel = adminPanel;
//
//    }

  
    @Operation(summary = "Get all total users")
    @GetMapping("/getAllUsers")
    private ResponseEntity<Integer> TotalUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllUsersCount());
    }


    @Operation(summary = "Get total likes counts")
    @GetMapping("/getAllLikes")
    private ResponseEntity<?> totalLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllLikesCount());
    }

    @Operation(summary = "Get all dislike counts")
    @GetMapping("/getAllDisLikes")
    private ResponseEntity<?> totalDisLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllDisLikeCount());
    }

    @Operation(summary = "Get all total post")
    @GetMapping("/getAllPost")
    private ResponseEntity<?> totalPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllPostCount());
    }

    @Operation(summary = "get all comments counts")
    @GetMapping("/getAllComments")
    private ResponseEntity<?> Comments(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsCount());
    }

    @Operation(summary = "get comments reply count")
    @GetMapping("/getAllCommentsReply")
    private ResponseEntity<?> getAllCommentsReply(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsReply());
    }

    @Operation(summary = "get all count data")
//    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getAllCountData")
    private ResponseEntity<AdminTotalInfoRes> getAllCountData(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCountData());
    }



    @Operation(summary = "get most used category")
    @GetMapping("/mostUsedCategory")
    private ResponseEntity<?> getMostUsedCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUsedCategory());
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "get top users ")
    @GetMapping("/topUser")
    private ResponseEntity<?> getTopUser(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUser());
    }

    @Operation(summary = "get top popular most")
    @GetMapping("/topPost")
    private ResponseEntity<?> getTopPost(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getPopularPost());
    }

    @Operation(summary = "get post increase or decrease in a day")
    @GetMapping("/postByMonth")
    private ResponseEntity<?> postByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getDailyPost());
    }

    @Operation(summary = "user increase/decrease in a month")
    @GetMapping("/userByMonth")
    private ResponseEntity<?> userByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.AddedUserInMonth());
    }


    @Operation(summary = "this is for creating admin with image")
    @PostMapping("/createAdmin")
    private ResponseEntity<?> createAdmin(@ModelAttribute UserReq userReq, @RequestParam("image") MultipartFile image){

        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.createUser(userReq, image, path));


    }


}

package com.example.Blog_Application2.controller;


import com.example.Blog_Application2.Service.AdminPanel;
import com.example.Blog_Application2.Service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminPanel adminPanel;


    public AdminController(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;

    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<Integer> TotalUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllUsersCount());
    }

    @GetMapping("/getAllLikes")
    private ResponseEntity<?> totalLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllLikesCount());
    }

    @GetMapping("/getAllDisLikes")
    private ResponseEntity<?> totalDisLikes(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllDisLikeCount());
    }

    @GetMapping("/getAllPost")
    private ResponseEntity<?> totalPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllPostCount());
    }

    @GetMapping("/getAllComments")
    private ResponseEntity<?> Comments(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsCount());
    }

    @GetMapping("/getAllCommentsReply")
    private ResponseEntity<?> getAllCommentsReply(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getAllCommentsReply());
    }


    @GetMapping("/mostUsedCategory")
    private ResponseEntity<?> getMostUsedCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUsedCategory());
    }

    @GetMapping("/topUser")
    private ResponseEntity<?> getTopUser(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getMostUser());
    }

    @GetMapping("/topPost")
    private ResponseEntity<?> getTopPost(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getPopularPost());
    }

    @GetMapping("/postByMonth")
    private ResponseEntity<?> postByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.getDailyPost());
    }

    @GetMapping("/userByMonth")
    private ResponseEntity<?> userByMonth(){
        return ResponseEntity.status(HttpStatus.OK).body(adminPanel.AddedUserInMonth());
    }


}

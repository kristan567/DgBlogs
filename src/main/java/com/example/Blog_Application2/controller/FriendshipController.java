package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.FriendshipService;
import com.example.Blog_Application2.models.Friendship;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Integer senderId, @RequestParam Integer receiverId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.sendFriendRequest(senderId, receiverId));
    }

    @GetMapping("/received")
    public ResponseEntity<List<Friendship>> getReceivedRequests(@RequestParam Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.getReceivedRequests(userId));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<Friendship>> getSentRequests(@RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.getSentRequests(userId));
    }

    @PostMapping("/respond")
    public ResponseEntity<String> respondToFriendRequest(@RequestParam Integer requestId, @RequestParam String status){

        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.respondToFriendRequest(requestId, status));
    }



}

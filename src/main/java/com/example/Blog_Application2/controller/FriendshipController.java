package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.FriendshipService;
import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.payloads.res.FriendPostRes;
import com.example.Blog_Application2.payloads.res.FriendshipRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "to send the friend request")
    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Integer senderId, @RequestParam Integer receiverId) {
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.sendFriendRequest(senderId, receiverId));
    }

    @Operation(summary = "view friend request by the received user")
    @GetMapping("/received")
    public ResponseEntity<List<Friendship>> getReceivedRequests(@RequestParam Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.getReceivedRequests(userId));
    }

    @Operation(summary = "view friend request by the sent user")
    @GetMapping("/sent")
    public ResponseEntity<List<Friendship>> getSentRequests(@RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.getSentRequests(userId));
    }

    @Operation(summary = "respond to the request")
    @PostMapping("/respond/{requestId}/{status}")
    public ResponseEntity<String> respondToFriendRequest(@PathVariable("requestId") Integer requestId, @PathVariable("status") String status){

        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.respondToFriendRequest(requestId, status));
    }

    @Operation(summary = "view your friends")
    @GetMapping("/listFriends")
    public ResponseEntity<List<FriendshipRes>> viewFriends(){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.ListFriends());
    }

    @Operation(summary = "see friends posts")
    @GetMapping("/friends-post")
    public ResponseEntity<List<PostRes>> ViewPostFromFriends(){
        return ResponseEntity.status(HttpStatus.OK).body(friendshipService.ViewPostFromFriends());
    }



}

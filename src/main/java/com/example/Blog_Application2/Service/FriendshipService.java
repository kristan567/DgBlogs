package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.payloads.res.FriendPostRes;
import com.example.Blog_Application2.payloads.res.FriendshipRes;
import com.example.Blog_Application2.payloads.res.PostRes;

import java.util.List;

public interface FriendshipService {

    String sendFriendRequest(Integer senderId, Integer receiverId);

    List<Friendship> getReceivedRequests(Integer userId);

    List<Friendship> getSentRequests(Long userId);

    String respondToFriendRequest(Integer requestId, String status);

    List<FriendshipRes> ListFriends();

    List<PostRes> ViewPostFromFriends();

    String DeleteFriendship( Integer friendId);

}

package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.FriendshipService;
import com.example.Blog_Application2.Service.mappers.FriendPostViewerMapper;
import com.example.Blog_Application2.Service.mappers.FriendshipMapper;
import com.example.Blog_Application2.Service.mappers.PostMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.enums.FriendRequestStatus;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.res.FriendPostRes;
import com.example.Blog_Application2.payloads.res.FriendshipRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import com.example.Blog_Application2.repository.FriendshipRepository;
import com.example.Blog_Application2.repository.PostRepository;
import com.example.Blog_Application2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class FriendshipServiceImpl implements FriendshipService {

    private final UserRepository userRepository;

    private final FriendshipRepository friendshipRepository;

    private final AuthenticationFacade authenticationFacade;

    private final FriendshipMapper friendshipMapper;

    private final FriendPostViewerMapper friendPostViewerMapper;

    private final PostRepository postRepository;

    private final PostMapper postMapper;



    public FriendshipServiceImpl(UserRepository userRepository, FriendshipRepository friendshipRepository, AuthenticationFacade authenticationFacade, FriendshipMapper friendshipMapper, FriendPostViewerMapper friendPostViewerMapper, PostRepository postRepository, PostMapper postMapper) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
//        this.friendRequestStatus = friendRequestStatus;
        this.authenticationFacade = authenticationFacade;
        this.friendshipMapper = friendshipMapper;
        this.friendPostViewerMapper = friendPostViewerMapper;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public String sendFriendRequest(Integer senderId, Integer receiverId) {

        Optional<User> sender = Optional.ofNullable(userRepository.findById(Long.valueOf(senderId)).orElseThrow(() -> new CustomException("user not found", HttpStatus.NOT_FOUND)));
        Optional<User> receiver = Optional.ofNullable(userRepository.findById(Long.valueOf(receiverId)).orElseThrow(() -> new CustomException("user not found", HttpStatus.NOT_FOUND)));

        if(sender.isPresent()||receiver.isPresent()){
            Optional<Friendship> existingRequest = friendshipRepository.findBySenderAndReceiver(sender.get(), receiver.get());
            if (existingRequest.isPresent()) {
                throw new RuntimeException("Friend request already sent");
            }
        }

        Friendship friendship = new Friendship();
        friendship.setSender(sender.get());
        friendship.setReceiver(receiver.get());
        friendship.setStatus(FriendRequestStatus.PENDING);
        friendshipRepository.save(friendship);

        return "friend request sent";
    }

    @Override
    public List<Friendship> getReceivedRequests(Integer userId) {

        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()){
        throw new RuntimeException("User Not Found");
        }

        return friendshipRepository.findByReceiverAndStatus(user.get(), FriendRequestStatus.PENDING);
    }

    public List<Friendship> getSentRequests(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        return friendshipRepository.findBySenderAndStatus(user.get(), FriendRequestStatus.PENDING);
    }

    @Override
    public String respondToFriendRequest(Integer requestId, String status) {

        FriendRequestStatus friendRequestStatus;
        Friendship friendship = friendshipRepository.findById(requestId).orElseThrow(()-> new RuntimeException("Friend request not found"));

        try{
            friendRequestStatus = FriendRequestStatus.valueOf(status.toUpperCase());
        }catch(Exception e){
            throw new RuntimeException("Invalid Status");
        }
        if(friendRequestStatus == FriendRequestStatus.PENDING){
          throw new RuntimeException("Request is still pending");
        }

        friendship.setStatus(friendRequestStatus);
        friendshipRepository.save(friendship);

         return "Friend request " + status.toLowerCase() + "!";
    }

    @Override
    public List<FriendshipRes> ListFriends(){
        Long userId = authenticationFacade.getAuthentication().getUserId();
          List<Friendship> friendshipList = friendshipRepository.findById(userId);
          List<FriendshipRes> res = new ArrayList<>();
          friendshipList.forEach(friendship->{
              res.add(friendshipMapper.toDtoTwo(friendship));
          });

          return res;
    }

//    @Override
//    public List<FriendPostRes> ViewPostFromFriends(){
//        Long userId = authenticationFacade.getAuthentication().getUserId();
//        List<FriendPostRes> postList = friendshipRepository.findPostByFriendsAsSender(userId);
//
//        List<FriendPostRes> res = new ArrayList<>();
//        postList.forEach(friendship->{res.add(friendPostViewerMapper.toDtoThree(friendship));
//        });
//
//        return res;
//
//
//    }

//    @Override
//    public List<FriendPostRes> ViewPostFromFriends(){
//        Long userId = authenticationFacade.getAuthentication().getUserId();
//        System.out.println("userId: "+userId);
//        List<FriendPostRes> friendList = friendshipRepository.findPostByFriends(userId);
//        // Step 2: Extract friend user IDs from friendship
//        List<Long> friendIds = friendList.stream()
//                .map(FriendPostRes::getReceiverId) // Assuming getReceiverId() exists
//                .collect(Collectors.toList());
//        // Step 3: Fetch posts using those friend IDs
//        List<Post> postList = postRepository.findAllByUserIdIn(friendIds);
//        List<FriendPostRes> res = new ArrayList<>();
//        postList.forEach(post->{res.add(friendPostViewerMapper.toDtoThree(post));
//        });
//
//        return res;
//    }

    @Override
    public List<PostRes> ViewPostFromFriends(){
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        List<Friendship> friendships = friendshipRepository.findBySenderOrReceiverAndStatus(userId, FriendRequestStatus.ACCEPTED);

//        System.out.println(friendships);

        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendships) {        //friendship takes one element from friendList
            if (friendship.getSender().equals(user)) {     //   checks if the user is equal to sender column in the friends
                friends.add(friendship.getReceiver());     //if the user is sender then it adds all the receiver friend of that sender to the list
            } else {
                friends.add(friendship.getSender());      // if it is not sender it may be receiver so it adds all the sender id to the friends list which is friend of the receiver
            }
        }

        List<Post> posts = this.postRepository.findByUserIn(friends);    //all the post is of that particular which is coming from friends list is taken out and added to the postList similar to the get all post


        return posts.stream()   //each value is added one by one
                .map(post -> postMapper.toDtoTwo(post)) // Use the injected postMapper here
                .collect(Collectors.toList());


    }

}

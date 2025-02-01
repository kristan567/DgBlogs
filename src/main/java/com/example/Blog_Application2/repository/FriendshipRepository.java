package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.enums.FriendRequestStatus;
import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.res.FriendPostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findByReceiverAndStatus(User receiver, FriendRequestStatus status);

    List<Friendship> findBySenderAndStatus(User sender, FriendRequestStatus status);

    Optional<Friendship> findBySenderAndReceiver(User sender, User receiver);


    List<Friendship> findById(Long userId);

    @Query(value="select f.* from Friendship f where (f.sender_id = :userId or f.receiver_id = :userId) and f.status = \"ACCEPTED\"", nativeQuery = true)
    List<Friendship> findBySenderOrReceiverAndStatus(Long userId, FriendRequestStatus status);

//    @Query(value="Select ur.email as receiver_email, pr.title as receiver_post_title, pr.content as receiver_post_content, f.receiver_id FROM friendship f JOIN users ur ON ur.id = f.receiver_id JOIN post pr ON ur.id = pr.user_id WHERE f.status = \"ACCEPTED\" AND f.sender_id = :userId", nativeQuery = true)
//    List<FriendPostRes> findPostByFriendsAsSender(Long userId);

//    @Query(value="select f.* from Friendship f where (f.sender_id = :userId or f.receiver_id = :userId) and f.status = \"ACCEPTED\"", nativeQuery = true)
//    List<FriendPostRes> findPostByFriends(Long userId);

    //    @Query(value="select f from friendship f where p")
//    List<Friendship> findPostByFriendsAsReceiver();

//    List<Friendship> findById(Long userId);



}

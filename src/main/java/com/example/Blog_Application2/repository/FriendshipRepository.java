package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.enums.FriendRequestStatus;
import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findByReceiverAndStatus(User receiver, FriendRequestStatus status);

    List<Friendship> findBySenderAndStatus(User sender, FriendRequestStatus status);

    Optional<Friendship> findBySenderAndReceiver(User sender, User receiver);


    List<Friendship> findById(Long userId);


//    List<Friendship> findById(Long userId);
}

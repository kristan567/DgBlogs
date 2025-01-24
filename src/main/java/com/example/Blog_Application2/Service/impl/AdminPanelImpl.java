package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.AdminPanel;
import com.example.Blog_Application2.Service.mappers.CategoryMapper;
import com.example.Blog_Application2.Service.mappers.PostMapper;
import com.example.Blog_Application2.Service.mappers.UserMapper;
import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.res.*;
import com.example.Blog_Application2.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminPanelImpl implements AdminPanel {

    private final LikeRepository likeRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentReplyRepository commentReplyRepository;

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    private final PostMapper postMapper;


    public AdminPanelImpl(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, CommentReplyRepository commentReplyRepository, UserMapper userMapper, CategoryMapper categoryMapper, PostMapper postMapper) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
        this.postMapper =  postMapper;
    }

    @Override
    public Integer getAllLikesCount() {
        return likeRepository.countToAlLikes(true);
    }

    @Override
    public Integer getAllDisLikeCount() {
        return likeRepository.countToAlDisLikes(true);
    }

    @Override
    public Integer getAllUsersCount() {
        return userRepository.countTotalUsers();
    }

    @Override
    public Integer getAllPostCount() {
        return postRepository.countTotalPosts();
    }

    @Override
    public Integer getAllCommentsCount() {
        return (int) commentRepository.count();
    }

    @Override
    public Integer getAllCommentsReply() {
        return (int) commentReplyRepository.count();
    }

    @Override
    public List<CategoryUsageRes> getMostUsedCategory() {
        List<Object[]> categoryUsage = postRepository.findCategoryUsage();
        if (!categoryUsage.isEmpty()) {
            List<CategoryUsageRes> result = new ArrayList<>();
            for (Object[] row : categoryUsage) {
                Category category = (Category) row[0];  // category ID
                Long occurrence = (Long) row[1];  // occurrence count
                CategoryRes categoryId = categoryMapper.toDtoTwo(category);
                result.add(new CategoryUsageRes(categoryId, occurrence));
            }
            return result;
        }
        // Handle case where no categories are found
        throw new IllegalStateException("No categories found");
    }

    @Override
    public List<TopUserRes> getMostUser() {
        List<Object[]> topUser = postRepository.findTopUser();
        if (!topUser.isEmpty()) {
            List<TopUserRes> result = new ArrayList<>();
            for (Object[] row : topUser) {
                User user = (User) row[0];
                Long occurrence = (Long) row[1];
                UserRes userId = userMapper.toDtoTwo(user);
                result.add(new TopUserRes(userId, occurrence));
            }
            return result;
        }
        // Handle case where no categories are found
        throw new IllegalStateException("Something Went Wrong");
    }

    @Override
    public List<TopPostRes> getPopularPost(){
        List<Object[]> topPost = likeRepository.findTopPost();
        if(!topPost.isEmpty()){
            List<TopPostRes> result = new ArrayList<>();
            for (Object[] row : topPost){
                 Post postId = (Post) row[0];
                 Long likeCount = (Long) row[1];
                 PostRes post = postMapper.toDtoTwo(postId);
                 result.add(new TopPostRes(post, likeCount));
            }
            return result;
        }
        throw new IllegalStateException("Something Went Wrong");
    }

    @Override
    public List<DailyPost> getDailyPost() {
        List<Object[]> dailyPostCount = postRepository.postCountByDay();
        if(!dailyPostCount.isEmpty()) {
            List<DailyPost> result = new ArrayList<>();
            for (Object[] row : dailyPostCount) {
                String day = (String) row[0];
                Long postId = (Long) row[1];
//                PostRes post = postMapper.toDtoTwo(postId);
                result.add(new DailyPost(postId, day));
            }
            return result;
        }else{
            throw new IllegalStateException("Something Went Wrong");
        }

    }

    @Override
    public List<AddedUserInMonth> AddedUserInMonth(){
        List<Object[]> userIncreased = userRepository.UserIncreasedInMonth();
        if(!userIncreased.isEmpty()){
            List<AddedUserInMonth> result = new ArrayList<>();
            for(Object[] row : userIncreased){
                String month = (String) row[0];
                Long userId = (Long) row[1];

                result.add(new AddedUserInMonth(userId, month));
            }
            return result;
        }else{
            throw new IllegalStateException("Something Went Wrong");
        }


    }


}

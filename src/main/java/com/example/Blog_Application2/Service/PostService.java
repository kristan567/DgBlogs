package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.example.Blog_Application2.payloads.res.PagePostRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import com.example.Blog_Application2.payloads.res.PostWithoutContentRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    //create
    PostRes createPost(PostReq postreq, Integer categoryId);

    PostRes createPostWithImage(PostReq postReq, MultipartFile image, String path);

    //update
    PostRes updatePost(PostReq postReq, Integer postId);

//    PostRes uploadImageAndUpdatePost(MultipartFile image, PostReq postReq, Integer PostId, String path);

    PostRes uploadImageAndUpdatePost(MultipartFile image, Integer PostId, String path);


    //delete
    String deletePost(Integer postId);

    //get all posts
    List<PostRes> getAllPost();

    List<PostWithoutContentRes> getAllPostsWithoutContent();

    PagePostRes getPostByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir );

    //get single post
    PostRes getPostById(Integer postId);

    //get all post by category
    List<PostRes> getPostByCategory(Integer categoryId);

    //get all posts by user
    List<PostRes> getPostsByUser(Integer userId);

    List<PostRes> getPostsByUserLoggedIn();

    //search posts
    List<PostRes> searchPosts(String Keyword);


    String sharePosts(Integer categoryId,Integer postId);







}

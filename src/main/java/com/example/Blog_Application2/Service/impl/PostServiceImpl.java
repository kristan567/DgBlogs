package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.FileService;
import com.example.Blog_Application2.Service.PostService;
import com.example.Blog_Application2.Service.mappers.PostMapper;
import com.example.Blog_Application2.Service.mappers.UserMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.payloads.req.CategoryReq;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.example.Blog_Application2.payloads.res.*;
import com.example.Blog_Application2.repository.CategoryRepo;
import com.example.Blog_Application2.repository.PostRepository;
import com.example.Blog_Application2.repository.UserRepository;
import com.example.Blog_Application2.utils.TransferObject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final CategoryRepo categoryRepo;
    private final FileService fileService;
    private final AuthenticationFacade authenticationFacade;
    private final UserMapper userMapper;


    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository, CategoryRepo categoryRepo, FileService fileService, AuthenticationFacade authenticationFacade, UserMapper userMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.categoryRepo = categoryRepo;
        this.fileService = fileService;
        this.authenticationFacade = authenticationFacade;
        this.userMapper =userMapper;
    }

    @Override
    public PostRes createPost(PostReq postReq, Integer categoryId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = this.userRepository.findById(userId).orElseThrow(()->new CustomException("User with Id "+ userId+" Not found", HttpStatus.NOT_FOUND));
        Category category = this.categoryRepo.findById(Long.valueOf(categoryId)).orElseThrow(()->new CustomException("Category with Id "+ categoryId + " Not found", HttpStatus.NOT_FOUND));

        int postCount = postRepository.countPostsByUserToday(userId);
        if (postCount >= 10) {
            throw new CustomException("You have reached the daily limit of 10 posts", HttpStatus.FORBIDDEN);
        }

        Post post = postMapper.toEntity((postReq));
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        postRepository.save(post);
        return postMapper.toDtoTwo(post);
    }

    @Override
    public PostRes createPostWithImage(PostReq postReq, MultipartFile image, String path) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = this.userRepository.findById(userId).orElseThrow(()->new CustomException("User with Id "+ userId+" Not found", HttpStatus.NOT_FOUND));
        Category category = this.categoryRepo.findById(Long.valueOf(postReq.getCategoryId())).orElseThrow(()->new CustomException("Category with Id "+ postReq.getCategoryId() + " Not found", HttpStatus.NOT_FOUND));

        Post post = postMapper.toEntity((postReq));
        String filename = fileService.uploadImage(path, image);
        if(filename == null){
            post.setImageName("default.png");
        }else{
            post.setImageName(filename);
        }
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        postRepository.save(post);
        return postMapper.toDtoTwo(post);
    }

    @Override
    public PostRes updatePost(PostReq postReq, Integer postId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException("post Not Found", HttpStatus.NOT_FOUND));
        if(userId != post.getUser().getId()){
            throw new CustomException("not a valid User to Update this post", HttpStatus.BAD_REQUEST);
        }

        post.setTitle(postReq.getTitle().trim());
        post.setContent(postReq.getContent().trim());
//        post.setImageName(postReq.getImageName().trim());
        post.setAddDate(new Date());

        post=postRepository.save(post);
        return postMapper.toDtoTwo(post);
    }

//    @Override
//    public PostRes uploadImageAndUpdatePost(MultipartFile image, PostReq postReq, Integer PostId, String path){
//        Long userId = authenticationFacade.getAuthentication().getUserId();
//        Post post = postRepository.findById(PostId).orElseThrow(()-> new CustomException("post Not Found", HttpStatus.NOT_FOUND));
//        if(userId != post.getUser().getId()){
//            throw new CustomException("not a valid User to Update this post", HttpStatus.BAD_REQUEST);
//        }
//        String filename = fileService.uploadImage(path, image);
//        post.setImageName(filename);
//        post.setTitle(postReq.getTitle().trim());
//        post.setContent(postReq.getContent().trim());
//        post.setAddDate(new Date());
//        postRepository.save(post);
//
//        return postMapper.toDtoTwo(post);
//    }

    @Override
    public PostRes uploadImageAndUpdatePost(MultipartFile image, Integer PostId, String path){
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Post post = postRepository.findById(PostId).orElseThrow(()-> new CustomException("post Not Found", HttpStatus.NOT_FOUND));
        if(userId != post.getUser().getId()){
            throw new CustomException("not a valid User to Update this post", HttpStatus.BAD_REQUEST);
        }
        String filename = fileService.uploadImage(path, image);
        post.setImageName(filename);
        postRepository.save(post);

        return postMapper.toDtoTwo(post);
    }



    @Override
    public String deletePost(Integer postId) {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        if(userId != post.getUser().getId()){
            throw new CustomException("not a valid User to delete this post", HttpStatus.BAD_REQUEST);
        }
        postRepository.deleteById(postId);

        return "Deleted Successfully";

    }

    @Override
    public List<PostRes> getAllPost() {

        Long userId = null;

        try {
            userId = authenticationFacade.getAuthentication().getUserId();
        } catch (Exception e) {
            userId = null;
        }

        if(userId != null) {


            List<Post> postList = postRepository.findAll();
            List<PostRes> res = new ArrayList<>();
            Long finalUserId = userId;
            postList.forEach(post -> {
                PostRes postRes = postMapper.toDtoTwo(post);
                if (finalUserId.equals(post.getUser().getId())) {
                    postRes.setDeletable(true);
                } else {
                    postRes.setDeletable(false);
                }

                res.add(postRes);
            });
            

            return res;

        }else {
          
            List<Post> postList = postRepository.findAll();

            List<PostRes> res = new ArrayList<>();
            postList.forEach(post-> {
                res.add(postMapper.toDtoTwo(post));
            });
            return res;
        }
    }

    @Override
    public List<PostWithoutContentRes> getAllPostsWithoutContent(){
        List<Object[]> postList = postRepository.getAllPostsWithoutContent();     //value is given to postlist with repo query in postereposiotry query takes objects as array in query to store multiple value
        List<PostWithoutContentRes> res = new ArrayList<>();     //  arraylist is created
        postList.forEach(row-> {     // mapping is done
            String title = (String) row[0];     //row of each object is defined
            Date addDate = (Date) row[1];
            String imageName = (String) row[2];
            Category categoryId = (Category) row[3];
            User user = (User) row[4];   // the value is directly taken from entity and given to the row
            UserRes userId = userMapper.toDtoTwo(user);  //mapper class is used to convert the user data to the userRes, i converted this into userres because PostWithoutContentRes has userRes has datatype in user
            PostWithoutContentRes postRes = new PostWithoutContentRes(title, imageName, addDate, categoryId, userId);    //value passed to the constructor
            res.add(postRes);
        });

        return res;
    }

    @Override
    public PagePostRes getPostByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort= Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(pageable);
        List<Post> content = pagePost.getContent();   //changing into list
        List<PostRes> res = new ArrayList<>();
        content.forEach(post-> {
            res.add(postMapper.toDtoTwo(post));
        });

        PagePostRes postres = new PagePostRes();
        postres.setPosts(res);
        postres.setPageNumber(pagePost.getNumber());
        postres.setPageSize(pagePost.getSize());
        postres.setTotalElements((int)pagePost.getTotalElements());

        postres.setTotalPages(pagePost.getTotalPages());
        postres.setLastPage(pagePost.isLast());

        return postres;
    }

    @Override
    public PostRes getPostById(Integer postId) {
        Long userId = null;

        try {
            userId = authenticationFacade.getAuthentication().getUserId();
        } catch (Exception e) {
            userId = null;
        }

        if(userId != null) {

            Optional<Post> post = postRepository.findById(postId);
            Post postEntity = post.orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
            PostRes postRes = postMapper.toDtoTwo(postEntity);
            if (Long.valueOf(postEntity.getUser().getId()).equals(userId)) {
                postRes.setDeletable(true);
            } else {
                postRes.setDeletable(false);
            }
            if (post.isEmpty())
                throw new CustomException("post with Id: " + postId + " not found", HttpStatus.NOT_FOUND);
            PostRes res = postMapper.toDtoTwo(post.get());
//        PostRes res = TransferObject.convert(post.get(), PostRes.class);
            return postRes;

        }else {

            Optional<Post> post = postRepository.findById(postId);
            if (post.isEmpty())
                throw new CustomException("post with Id: " + postId + " not found", HttpStatus.NOT_FOUND);
            PostRes res = postMapper.toDtoTwo(post.get());
//        PostRes res = TransferObject.convert(post.get(), PostRes.class);
            return res;
        }
    }

    public List<PostRes> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(Long.valueOf(categoryId)).orElseThrow(()->new CustomException("Blog with: " + categoryId + " category not found ", HttpStatus.NOT_FOUND));
        List<Post> posts = this.postRepository.findByCategory(category);
        return posts.stream().map((post)-> postMapper.toDtoTwo(post)).collect(Collectors.toList());
    }

//    @Override
//    public List<PostRes> getPostByCategory(Integer categoryId) {
//        Category category = this.categoryRepo.findById(Long.valueOf(categoryId)).orElseThrow(()->new CustomException("Blog with: " + categoryId + " category not found ", HttpStatus.NOT_FOUND));
//        List<Post> posts = this.postRepository.findByCategory(category);
//        Long userId = null;
//        try {
//            userId = authenticationFacade.getAuthentication().getUserId();
//        } catch (Exception e) {
//            // Log this error if needed (e.g., unauthenticated user accessing the endpoint)
//            userId = null; // Default to unauthenticated
//        }
//        if(userId == null){
//            return posts.stream()
//                    .map(post -> {
//                        PostRes postRes = postMapper.toDtoTwo(post);
//                        postRes.excludeContent(); // Exclude content if the user is not authenticated
//                        return postRes;
//                    })
//                    .collect(Collectors.toList());
//        }
//        return posts.stream().map((post)-> postMapper.toDtoTwo(post)).collect(Collectors.toList());
//    }

    @Override
    public List<PostRes> getPostsByUser(Integer userId) {
        User user = this.userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("user with Id: "+ userId+ "Not Found", HttpStatus.NOT_FOUND));
        List<Post> posts = this.postRepository.findByUser(user);

        return posts.stream()
                .map(post -> postMapper.toDtoTwo(post)) // Use the injected postMapper here
                .collect(Collectors.toList());
    }

    @Override
    public List<PostRes> getPostsByUserLoggedIn() {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = this.userRepository.findById(userId).orElseThrow(()->new CustomException("user with Id: "+ userId+ "Not Found", HttpStatus.NOT_FOUND));
        List<Post> posts = this.postRepository.findByUser(user);

        return posts.stream()
                .map(post -> postMapper.toDtoTwo(post)) // Use the injected postMapper here
                .collect(Collectors.toList());
    }


    @Override
    public List<PostRes> searchPosts(String Keyword) {
        List<Post> posts = this.postRepository.searchByTitle("%"+ Keyword+ "%");

        return posts.stream().map((post)->this.postMapper.toDtoTwo(post))
                .collect(Collectors.toList());
    }


    @Override
        public String sharePosts(Integer categoryId,Integer postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException("Post Not Found", HttpStatus.NOT_FOUND));
        Integer category = post.getCategory().getCategoryId();


        String postURl = "http://localhost:5173/posts/";

        return postURl + categoryId + "/"+ postId;


        }

}

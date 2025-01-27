package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.FileService;
import com.example.Blog_Application2.Service.PostService;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.constant.AppConstants;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.example.Blog_Application2.payloads.res.PostRes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController

@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    private AuthenticationFacade authenticationFacade;

    @PostMapping("/category/{categoryId}/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostReq postReq, @PathVariable Integer categoryId){

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postReq, categoryId));
    }

    @PostMapping("/category/{categoryId}/image/posts")
    public ResponseEntity<?> createPostWithImage(@ModelAttribute PostReq postReq,@RequestParam("image") MultipartFile image, @PathVariable Integer categoryId){

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPostWithImage(postReq, categoryId,image, path));

    }

    @PutMapping("/post-update/{postId}")
    public ResponseEntity<?> updatePost( @RequestBody PostReq postReq, @PathVariable Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postReq, postId));
    }

    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostRes>>  getPostsByUser(@PathVariable Integer userId){
        List<PostRes> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostRes>>(posts, HttpStatus.OK);
    }

    @GetMapping("authUser/posts")
    public ResponseEntity<List<PostRes>>  getPostsByUserLoggedIn(){
        List<PostRes> posts = this.postService.getPostsByUserLoggedIn();
        return new ResponseEntity<List<PostRes>>(posts, HttpStatus.OK);
    }



    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostRes>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostRes> posts = this.postService.getPostByCategory(categoryId);    //euta list rakhxa but id haru ko type hunxa which is PostRes so list gayera only tha kind of category which is present in postRes
        return new ResponseEntity<List<PostRes>>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
    }
    @GetMapping("/postWithoutContent")
    public ResponseEntity<?> getAllPostsWithoutContent(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsWithoutContent());
    }

    @GetMapping("/posts-page")
    public ResponseEntity<?> getPostByPage(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber
                                           ,@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize
                                           ,@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false)String sortBy
                                           ,@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByPage(pageNumber, pageSize, sortBy, sortDir));

    }


    @DeleteMapping("/posts-delete/{postId}")
    public ResponseEntity<?> deletePosts(@PathVariable("postId") Integer postId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(postId));
    }

    @GetMapping("posts/search/{keywords}")
    public ResponseEntity<?> searchPostByTitle( @PathVariable("keywords") String keywords){
//        List<PostRes> result = this.postService.searchPosts(keywords);
//        return ResponseEntity<List<PostRes>>(result,HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(postService.searchPosts(keywords));
    }


    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<?> fileUpload(@RequestParam("image") MultipartFile image,
                                                    @PathVariable Integer postId) throws IOException {


        return ResponseEntity.status(HttpStatus.OK).body(postService.uploadImageAndUpdatePost(image, postId, path));

    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}

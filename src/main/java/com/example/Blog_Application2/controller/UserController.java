package com.example.Blog_Application2.controller;


import com.example.Blog_Application2.Service.FileService;
import com.example.Blog_Application2.Service.UserService;
import com.example.Blog_Application2.config.jwt.JwtUtil;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.constant.AppConstants;
import com.example.Blog_Application2.payloads.req.LoginReq;
import com.example.Blog_Application2.payloads.req.ResetPassReq;
import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.LoginRes;
import com.example.Blog_Application2.utils.AppConstant;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RestController
public class UserController {

    private final  UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final AuthenticationFacade facade;

    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, AuthenticationFacade facade, FileService fileService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.facade = facade;
        this.fileService =  fileService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> save(@Valid @RequestBody UserReq user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/signupWithImage")
    public ResponseEntity<?> saveWithImage(@ModelAttribute UserReq userReq, @RequestParam("image") MultipartFile image){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserWithImage(userReq, image,path));
    }

    @PostMapping("/create-Admin")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserReq userReq ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userReq));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {        //marks the format that it should be from loginreq
        Authentication authentication = authenticationManager.authenticate(        //object creation but with authenticating the value of token
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);          //The SecurityContext is used throughout the application to identify the currently authenticated user.
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginReq.getUsername());        //feteh user details from getusername and pass to the token for generation
        final String token = jwtUtil.generateToken(userDetails);      //  Calls a utility class (jwtUtil) to create a JWT token using the user's details.
        return ResponseEntity.ok().body(new LoginRes(token));       //Sends the token back to the client, which will store it (usually in localStorage, sessionStorage, or cookies) and include it in future requests for authentication.
    }

    @GetMapping("/logged-in-user")
    public ResponseEntity<?> getLoggedInUser() {
        long userId = facade.getAuthentication().getUserId();
       return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("/getUser-auth")
    public ResponseEntity<?> getUserByAuthentication() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUserAuthentication());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserReq user) {
        return ResponseEntity.ok().body(userService.updateById(id, user));
    }


//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok().body(userService.deleteById());
    }


    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassReq passReq) {
        return ResponseEntity.ok().body(userService.resetPassword(passReq));
    }


    @GetMapping("/users-page")
    public ResponseEntity<?> getUserByPage(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber
            ,@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize
            ,@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false)String sortBy
            ,@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByPage(pageNumber, pageSize, sortBy, sortDir));

    }

    @GetMapping(value = "/user/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }



}

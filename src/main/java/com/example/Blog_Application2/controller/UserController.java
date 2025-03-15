package com.example.Blog_Application2.controller;


import com.example.Blog_Application2.Service.FileService;
import com.example.Blog_Application2.Service.UserService;
import com.example.Blog_Application2.config.jwt.JwtUtil;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.constant.AppConstants;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.LoginReq;
import com.example.Blog_Application2.payloads.req.ResetPassReq;
import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.LoginRes;
import com.example.Blog_Application2.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;


@RestController
public class UserController {

    private final  UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final AuthenticationFacade facade;

    private final FileService fileService;

    private final UserRepository userRepository;

    @Value("${project.image}")
    private String path;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, AuthenticationFacade facade, FileService fileService, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.facade = facade;
        this.fileService =  fileService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "sign up for user")
    @PostMapping("/signup")
    public ResponseEntity<?> save(@Valid @RequestBody UserReq user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @Operation(summary = "sign up with image")
    @PostMapping("/signupWithImage")
    public ResponseEntity<?> saveWithImage(@ModelAttribute UserReq userReq, @RequestParam("image") MultipartFile image){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserWithImage(userReq, image,path));
    }

    @Operation(summary = "crate user by admin")
    @PostMapping("/create-Admin")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserReq userReq ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userReq));
    }

    @Operation(summary = "login for the user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {        //marks the format that it should be from loginreq
        Authentication authentication = authenticationManager.authenticate(        //object creation but with authenticating the value of token    // authentication token is generated from username and password, authenticate validates the credentials
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);          //The SecurityContext is used throughout the application to identify the currently authenticated user.(currently logged in user)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginReq.getUsername());        //feteh user details from getusername and pass to the token for generation
        if(userDetails!= null){
            User user = userRepository.findUser(loginReq.getUsername());
            user.setActive(true);
            userRepository.save(user);
        }
        final String token = jwtUtil.generateToken(userDetails);      //  Calls a utility class (jwtUtil) to create a JWT token using the user's details.
        return ResponseEntity.ok().body(new LoginRes(token));       //Sends the token back to the client, which will store it (usually in localStorage, sessionStorage, or cookies) and include it in future requests for authentication.
    }

    @Operation(summary = "user active status is false")
    @PostMapping("/user-logout")
    public String loginOut(){
        long userId = facade.getAuthentication().getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        user.setActive(false);
        userRepository.save(user);

        return "user is logged out";
    }



    @Operation(summary = "confirm if user is logged in successfully")
    @GetMapping("/logged-in-user")
    public ResponseEntity<?> getLoggedInUser() {
        long userId = facade.getAuthentication().getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()){
                User user = userOptional.get();
                user.setActive(true);
                userRepository.save(user);
            return new ResponseEntity<>("User is online", HttpStatus.OK);
        }else{
            User user = userOptional.get();
            user.setActive(false);
            userRepository.save(user);
            return new ResponseEntity<>("User is Offline", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "get user by the id")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @Operation(summary = "get user who is authenticated")
    @GetMapping("/getUser-auth")
    public ResponseEntity<?> getUserByAuthentication() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUserAuthentication());
    }

    @Operation(summary = "update the user")
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser( @Valid @RequestBody UserReq user) {
        return ResponseEntity.ok().body(userService.updateById( user));
    }

    @Operation(summary = "update user with image only")
    @PutMapping("/update-userImage")
    public ResponseEntity<?> updateUserWithImage(@RequestParam("image") MultipartFile image){
        return ResponseEntity.ok().body(userService.updateUserImage(image, path));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "delete the user")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok().body(userService.deleteById());
    }


    @Operation(summary = "get users")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


    @Operation(summary = "users can reset the password")
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassReq passReq) {
        return ResponseEntity.ok().body(userService.resetPassword(passReq));
    }

    @Operation(summary = "get user by page")
    @GetMapping("/users-page")
    public ResponseEntity<?> getUserByPage(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber
            ,@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize
            ,@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false)String sortBy
            ,@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false)String sortDir){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByPage(pageNumber, pageSize, sortBy, sortDir));
    }

    @Operation(summary = "get user image")
    @GetMapping(value = "/user/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }



}

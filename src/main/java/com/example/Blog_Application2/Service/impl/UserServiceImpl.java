package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.EmailService;
import com.example.Blog_Application2.Service.FileService;
import com.example.Blog_Application2.Service.mappers.UserMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.enums.Role;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.Service.UserService;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.payloads.req.MailBody;
import com.example.Blog_Application2.payloads.req.ResetPassReq;
import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.PageUserRes;
import com.example.Blog_Application2.payloads.res.UserRes;

import com.example.Blog_Application2.repository.UserRepository;
import com.example.Blog_Application2.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;
    private final AuthenticationFacade facade;
    private AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final FileService fileService;


    @Override
    public UserRes save(UserReq userReq) {
        if(!userReq.getPassword().equals(userReq.getConfirmPassword()))
            throw new CustomException("Password should match", HttpStatus.BAD_REQUEST);
        if (userRepository.existsByEmail(userReq.getEmail())) {
            throw new CustomException("Email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.toEntity(userReq);
//        user.setId(userRepository.findNextId());
        user.setAddDate(new Date());
        if (user.getRole()==null ){
            user.setRole(Role.USER);
        }
        userRepository.save(user);

        String userEmail = userReq.getEmail();


        MailBody mailBody = MailBody.builder()
                .to(userEmail)
                .subject("Account Creation")
                .text("Your Account Has Been created in DgBlogs")
                .build();

        emailService.sendSimpleMessage(mailBody);

        return findById(user.getId());
    }

    @Override
    public UserRes createUserWithImage(UserReq userReq, MultipartFile image, String path){
        if(!userReq.getPassword().equals(userReq.getConfirmPassword()))
            throw new CustomException("Password should match", HttpStatus.BAD_REQUEST);
        if (userRepository.existsByEmail(userReq.getEmail())) {
            throw new CustomException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.toEntity(userReq);
        String filename = fileService.uploadImage(path, image);
        user.setImageName(filename);
        user.setAddDate(new Date());
        if (user.getRole()==null ){
            user.setRole(Role.USER);
        }
        userRepository.save(user);

        String userEmail = userReq.getEmail();


        MailBody mailBody = MailBody.builder()
                .to(userEmail)
                .subject("Account Creation")
                .text("Your Account Has Been created in DgBlogs")
                .build();

        emailService.sendSimpleMessage(mailBody);

        return userMapper.toDtoTwo(user);

    }

    @Override
    public UserRes createUser(UserReq userReq) {
        if(!userReq.getPassword().equals(userReq.getConfirmPassword())){
            throw new CustomException("The Password did not match", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(userReq.getEmail())){
            throw new CustomException("user already exists", HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.toEntity(userReq);
        if (user.getRole()==null ){
            user.setRole(Role.ADMIN);
        }
         userRepository.save(user);


        String userEmail = userReq.getEmail();
        String userPassword = userReq.getPassword();

        MailBody mailBody = MailBody.builder()
                .to(userEmail)
                .text("Your Account Has Been created By Credentials\n" +"Your Username: "+ userEmail + "\n"+"Your Password: "+ userPassword)
                .subject("Account Creation")
                .build();

        emailService.sendSimpleMessage(mailBody);
        return userMapper.toDtoTwo(user);

    }

    @Override
    public UserRes findById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new CustomException("User not Found", HttpStatus.NOT_FOUND);

        return TransferObject.convert(user.get(), UserRes.class);
    }

    @Override
    public UserRes findByUserAuthentication(){
        long userId=facade.getAuthentication().getUserId();
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new CustomException("User not Found", HttpStatus.NOT_FOUND);

        return TransferObject.convert(user.get(), UserRes.class);

    }

    @Override
    public UserRes findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new CustomException("User Not Found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(user.get(), UserRes.class);
    }

    @Override
    public String resetPassword(ResetPassReq passReq) {
        long userId=facade.getAuthentication().getUserId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        User currentUser = user.get();
        if (!passwordEncoder.matches(passReq.getOldPassword(), currentUser.getPassword())) {
            throw new CustomException("Old password is not correct", HttpStatus.BAD_REQUEST);
        }
        if(!passReq.getNewPassword().equals(passReq.getConfirmPassword()))
            throw new CustomException("Password should match", HttpStatus.BAD_REQUEST);

        if(passReq.getOldPassword().equals(passReq.getNewPassword()))
            throw new CustomException("Password must be different then older ones", HttpStatus.BAD_REQUEST);

        currentUser.setPassword(passwordEncoder.encode(passReq.getNewPassword()));
        userRepository.save(currentUser);

        String userEmail = currentUser.getEmail();

        MailBody mailBody = MailBody.builder()
                .to(userEmail)
                .text("Your Password was changed successfully")
                .subject("Password Change")
                .build();

        emailService.sendSimpleMessage(mailBody);

        return "Password changed successfully";
    }

    @Override
    public UserRes updateById( UserReq userReq) {
        Long userId = authenticationFacade.getAuthentication().getUserId();

        User user = userRepository.findById(userId).get();
        if(userId != user.getId()){
            throw new CustomException("user is not valid to update current user", HttpStatus.BAD_REQUEST);
        }
        user.setFirstName(userReq.getFirstName().trim());
        user.setMiddleName(userReq.getMiddleName().trim());
        user.setLastName(userReq.getLastName().trim());
    //        if (userRepository.existsByEmail(userReq.getEmail())) {
    //            throw new CustomException("Email already exists", HttpStatus.BAD_REQUEST);
    //        }
//        user.setEmail(userReq.getEmail().trim());
        user.setPhone(userReq.getPhone().trim());
        userRepository.save(user);
        return findById(user.getId());
    }

    @Override
    public String deleteById() {
        Long userId = authenticationFacade.getAuthentication().getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        // Get the User entity
        User user = userOptional.get();

        // Ensure the authenticated user is the same as the user being deleted
        if (!userId.equals(user.getId())) {
            throw new CustomException("You are not authorized to delete this user", HttpStatus.FORBIDDEN);
        }
        userOptional.get().setActive(false);
        userRepository.save(userOptional.get());
        userRepository.deleteById(userId);
        return "User Deleted Successfully";
    }

    @Override
    public List<UserRes> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserRes> res = new ArrayList<>();
        users.forEach(user -> {
            UserRes userRes = TransferObject.convert(user, UserRes.class);
            res.add(userRes);
        });
        return res;
    }

    @Override
    public PageUserRes getUserByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir ){
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort= Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> pageUser = this.userRepository.findAll(pageable);
        List<User> content = pageUser.getContent();

        List<UserRes> res = new ArrayList<>();
        content.forEach(user->{
            res.add(userMapper.toDtoTwo(user));
        });


        PageUserRes userres =new PageUserRes();
        userres.setUsers(res);
        userres.setPageNumber(pageUser.getNumber());
        userres.setPageSize(pageUser.getSize());
        userres.setTotalElements((int)pageUser.getTotalElements());

        userres.setTotalPages(pageUser.getTotalPages());
        userres.setLastPage(pageUser.isLast());

        return userres;

    }
}

package com.example.Blog_Application2.controller;


import com.example.Blog_Application2.Service.EmailService;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.ForgotPassword;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.ChangePassword;
import com.example.Blog_Application2.payloads.req.MailBody;
import com.example.Blog_Application2.repository.ForgotPasswordRepository;
import com.example.Blog_Application2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final ForgotPasswordRepository forgotPasswordRepository;


    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;

    }

    //send email for email verification
        @PostMapping("/verifyPassword/{email}")
        public ResponseEntity<String> verificationEmail(@PathVariable String email){
            User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException("user not found", HttpStatus.NOT_FOUND));

            int otp = otpGenerator();
            MailBody mailBody = MailBody.builder()
                    .to(email)
                    .text("This is the OTP For the Forgot Password Request: " + otp)
                    .subject("OTP for Forgo Password request")
                    .build();

            ForgotPassword fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis()+ 70 * 1000))
                    .user(user)
                    .build();
            emailService.sendSimpleMessage(mailBody);
            forgotPasswordRepository.save(fp);

            return ResponseEntity.ok("email sent for verification");

        }

     @PostMapping("/verification/{otp}/{email}")
     public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){
         User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException("user not found", HttpStatus.NOT_FOUND));
         ForgotPassword fp = forgotPasswordRepository.findOtpAndUser(otp,user).orElseThrow(()-> new CustomException("invalid Otp for email: " + email, HttpStatus.NOT_FOUND));


         if(fp.getExpirationTime().before(Date.from(Instant.now()))){
             forgotPasswordRepository.deleteById(fp.getId());
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Otp has Expired");
         }

         fp.setVerified(true);
         forgotPasswordRepository.save(fp);

         return ResponseEntity.ok("OTP Verified");

     }


    @PostMapping("changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email){

        if(!Objects.equals(changePassword.password(), changePassword.confirmPassword())){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Please Enter the password again");
        }
        String encodedPassword = passwordEncoder.encode(changePassword.password());
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("user not found", HttpStatus.NOT_FOUND));
        ForgotPassword fp = forgotPasswordRepository.findByUserId(user).orElseThrow(()-> new CustomException("invalid Otp for email: " + email, HttpStatus.NOT_FOUND));

        if (!fp.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Password change can only be done after verification");
        }

        userRepository.updatePassword(email, encodedPassword);
        fp.setVerified(false);
        forgotPasswordRepository.save(fp);
        forgotPasswordRepository.deleteById(fp.getId());

        return ResponseEntity.ok("password has been changed successfully");

    }


    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_00, 999_999);
    }



}

package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.MailBody;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public interface EmailService {

//    private final JavaMailSender javaMailSender;
//
//
//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    public void sendSimpleMessage(MailBody mailBody);
//
//        SimpleMailMessage message = new SimpleMailMessage();     //using this class the mail is drafted
//
//        message.setTo(mailBody.to());
//        message.setFrom("krm56712@gmail.com");
//        message.setSubject(mailBody.subject());
//        message.setText(mailBody.text());
//
//
//        javaMailSender.send(message);


//    }

}

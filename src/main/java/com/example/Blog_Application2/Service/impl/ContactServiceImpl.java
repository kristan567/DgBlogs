package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.ContactService;
import com.example.Blog_Application2.Service.mappers.ContactMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Contact;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.res.ContactRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import com.example.Blog_Application2.repository.ContactRepository;
import com.example.Blog_Application2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;

    private final ContactMapper contactMapper;


    public ContactServiceImpl(ContactRepository contactRepository, AuthenticationFacade authenticationFacade, UserRepository userRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
        this.contactMapper = contactMapper;
    }



    @Override
    public ContactRes createContact(ContactReq contactReq) {

        Long userId = authenticationFacade.getAuthentication().getUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException("User Not found", HttpStatus.NOT_FOUND ));

        Contact contact = contactMapper.toEntity(contactReq);

        contact.setUser(user);

        contactRepository.save(contact);

        return contactMapper.toDtoTwo(contact);
    }

    @Override
    public List<ContactRes> viewContact(){

        List<Contact> contactList = contactRepository.findAll();

        List<ContactRes> res = new ArrayList<>();

        contactList.forEach(contact -> {
            res.add(contactMapper.toDtoTwo(contact));
        });

        return res;
    }

    public List<ContactRes> viewSpecificUserContact( Integer userId){

        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new CustomException("user Not found", HttpStatus.NOT_FOUND));

        List<Contact> contacts = contactRepository.findByUser(user);


        return contacts.stream()
                .map(contact -> contactMapper.toDtoTwo(contact))
                .collect(Collectors.toList());
    }

}

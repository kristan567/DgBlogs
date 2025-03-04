package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.ContactService;
import com.example.Blog_Application2.Service.mappers.ContactMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Contact;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.res.ContactRes;
import com.example.Blog_Application2.repository.ContactRepository;
import com.example.Blog_Application2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}

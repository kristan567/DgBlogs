package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.res.ContactRes;

import java.util.List;

public interface ContactService {


    ContactRes createContact(ContactReq contactReq);

    List<ContactRes> viewContact();

}

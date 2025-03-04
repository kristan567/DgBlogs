package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.res.ContactRes;

public interface ContactService {


    ContactRes createContact(ContactReq contactReq);

}

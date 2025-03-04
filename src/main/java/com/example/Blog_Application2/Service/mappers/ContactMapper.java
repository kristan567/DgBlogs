package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Contact;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.example.Blog_Application2.payloads.res.ContactRes;
import com.example.Blog_Application2.payloads.res.PostRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface ContactMapper extends MapperClass<Contact, ContactReq, ContactRes> {


    Contact toEntity(ContactReq contactReq);


    ContactRes toDtoTwo(Contact contact);
}

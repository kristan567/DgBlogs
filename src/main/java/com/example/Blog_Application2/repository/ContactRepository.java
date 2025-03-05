package com.example.Blog_Application2.repository;

import com.example.Blog_Application2.models.Contact;
import com.example.Blog_Application2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {


    List<Contact> findByUser( User user);



}

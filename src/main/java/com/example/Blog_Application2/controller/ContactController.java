package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.ContactService;
import com.example.Blog_Application2.payloads.req.ContactReq;
import com.example.Blog_Application2.payloads.req.PostReq;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")

public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "create contact")
    @PostMapping("/submit")
    public ResponseEntity<?> createContact(@Valid @RequestBody ContactReq contactReq){
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(contactReq));
    }

    @Operation(summary = "view contactus")
    @GetMapping("/view")
    public ResponseEntity<?> createContact(){
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.viewContact());
    }






}

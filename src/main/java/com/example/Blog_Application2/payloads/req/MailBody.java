package com.example.Blog_Application2.payloads.req;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {


}

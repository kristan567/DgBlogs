package com.example.Blog_Application2.exception;

//import lombok.builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMessage dto;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {

        super(message);
        this.httpStatus = httpStatus;
        dto = ErrorMessage.builder().message(message).statusCode(httpStatus.value()).build();
    }


}

package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.UserReq;
import com.example.Blog_Application2.payloads.res.UserRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component

public interface UserMapper extends MapperClass<User, UserReq, UserRes> {

    @Mapping(target = "id", ignore =true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")

    User toEntity(UserReq userReq);

    @Mapping(target = "password", defaultValue = "*********")
    UserReq toDto(User user);

    @Mapping(target = "email")
    UserRes toDtoTwo(User user);

    @Named("encodePassword")
    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}

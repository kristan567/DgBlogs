package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.models.Like;
import com.example.Blog_Application2.payloads.req.FriendshipReq;
import com.example.Blog_Application2.payloads.res.FriendshipRes;
import com.example.Blog_Application2.payloads.res.LikeRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring",  uses = {FriendshipMapper.class})
@Component
public interface FriendshipMapper extends MapperClass<Friendship, FriendshipReq, FriendshipRes> {

    @Mapping( source = "receiver", target = "user")
    FriendshipRes toDtoTwo(Friendship friendship);
}

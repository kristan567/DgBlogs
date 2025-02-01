package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Friendship;
import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.payloads.res.FriendPostRes;
import com.example.Blog_Application2.payloads.res.FriendshipRes;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",  uses = {FriendPostViewerMapper.class})
@Component
public interface FriendPostViewerMapper extends MapperClass<Post, FriendPostRes, FriendshipRes> {

    FriendPostRes toDtoThree(Post post);

    FriendPostRes toDtoThree(FriendPostRes friendPostRes);

}

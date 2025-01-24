package com.example.Blog_Application2.Service.mappers;


import com.example.Blog_Application2.models.Post;
import com.example.Blog_Application2.payloads.req.PostReq;
import com.example.Blog_Application2.payloads.res.PostRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface PostMapper extends MapperClass<Post, PostReq, PostRes> {

    @Mapping(target = "postId", ignore = true)
    Post toEntity(PostReq postReq);

    @Mapping(target = "postId")
    PostRes toDtoTwo(Post post);









}

package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Like;
import com.example.Blog_Application2.payloads.req.LikeReq;
import com.example.Blog_Application2.payloads.res.LikeRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper extends MapperClass<Like, LikeReq, LikeRes> {

    @Mapping(target = "id", ignore = true)
    Like toEntity(LikeReq likeReq);

//    @Mapping(target = "id")
    LikeRes toDtoTwo(Like like);

//    @Mapping(target="id")
    LikeReq toDto(Like like);

}

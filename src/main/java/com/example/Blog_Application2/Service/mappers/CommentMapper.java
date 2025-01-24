package com.example.Blog_Application2.Service.mappers;


import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.payloads.req.CommentReq;
import com.example.Blog_Application2.payloads.res.CommentRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper extends MapperClass<Comment, CommentReq,CommentRes> {

    @Mapping(target = "id", ignore=true)
    Comment toEntity(CommentReq commentReq);

    @Mapping(target = "id")
    CommentRes toDtoTwo(Comment comment);

    @Mapping(target= "id")
    CommentReq toDto(Comment comment);

}

package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Comment;
import com.example.Blog_Application2.models.CommentReply;
import com.example.Blog_Application2.payloads.req.CommentReplyReq;
import com.example.Blog_Application2.payloads.req.CommentReq;
import com.example.Blog_Application2.payloads.res.CommentReplyRes;
import com.example.Blog_Application2.payloads.res.CommentRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface CommentReplyMapper extends MapperClass<CommentReply, CommentReplyReq, CommentReplyRes> {

    @Mapping(target= "id", ignore=true)
    CommentReply toEntity(CommentReplyReq commentReplyReq);

    @Mapping(target="id")
    CommentReplyRes toDtoTwo(CommentReply commentReply);

    @Mapping(target="id")
    CommentReplyReq toDto(CommentReply CommentReply);







}

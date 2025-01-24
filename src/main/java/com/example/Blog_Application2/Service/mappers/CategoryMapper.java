package com.example.Blog_Application2.Service.mappers;

import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.payloads.req.CategoryReq;
import com.example.Blog_Application2.payloads.res.CategoryRes;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends MapperClass<Category, CategoryReq, CategoryRes> {


    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CategoryReq categoryReq);

    CategoryRes toDtoTwo(Category category);


}

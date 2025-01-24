package com.example.Blog_Application2.Service;

import com.example.Blog_Application2.payloads.req.CategoryReq;
import com.example.Blog_Application2.payloads.res.CategoryRes;

import java.util.List;

public interface CategoryService {

    //create
    CategoryRes createCategory(CategoryReq categoryReq);

    //update
    CategoryRes updateCategory(CategoryReq categoryReq, Integer categoryId);

    //delete
    String deleteCategory(Integer categoryId);

    //get
    CategoryRes getCategory(Integer categoryId);

    //get All
    List<CategoryRes> getAllCategory();


}

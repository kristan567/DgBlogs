package com.example.Blog_Application2.Service.impl;

import com.example.Blog_Application2.Service.CategoryService;
import com.example.Blog_Application2.Service.mappers.CategoryMapper;
import com.example.Blog_Application2.config.secuirty.AuthenticationFacade;
import com.example.Blog_Application2.exception.CustomException;
import com.example.Blog_Application2.models.Category;
import com.example.Blog_Application2.models.User;
import com.example.Blog_Application2.payloads.req.CategoryReq;
import com.example.Blog_Application2.payloads.res.CategoryRes;
import com.example.Blog_Application2.repository.CategoryRepo;
import com.example.Blog_Application2.utils.TransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    private final CategoryMapper categoryMapper;

    private final AuthenticationFacade authenticationFacade;

    public CategoryServiceImpl(CategoryRepo categoryRepo, CategoryMapper categoryMapper, AuthenticationFacade authenticationFacade) {
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public CategoryRes getCategory(Integer categoryId) {
        Optional<Category> cat = categoryRepo.findById(Long.valueOf(categoryId));
        if(cat.isEmpty()) throw new CustomException(" Not found ", HttpStatus.NOT_FOUND);

        return TransferObject.convert(cat.get(), CategoryRes.class);
    }

    @Override
    public CategoryRes createCategory(CategoryReq categoryReq) {
        Category category = categoryMapper.toEntity((categoryReq));
//        category.setCategoryId(categoryRepo.findNextId());
        categoryRepo.save(category);   //try to convert directly

        CategoryRes cat = categoryMapper.toDtoTwo(category);

//        return getCategory(category.getCategoryId());
        return cat;
    }

    @Override
    public CategoryRes updateCategory(CategoryReq categoryReq, Integer categoryId) {
        Category cat = categoryRepo.findById(Long.valueOf(categoryId)).get();
        cat.setCategoryTitle(categoryReq.getCategoryTitle().trim());
        cat.setCategoryDescription(categoryReq.getCategoryDescription().trim());
        categoryRepo.save(cat);

        return getCategory(cat.getCategoryId());
    }

    @Override
    public String deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(Long.valueOf(categoryId)).orElseThrow(()-> new CustomException("Category ", HttpStatus.BAD_REQUEST ));
        this.categoryRepo.delete(cat);

        return "user deleted Successfully";
    }

    @Override
    public List<CategoryRes> getAllCategory() {
        List<Category> categoriesList = this.categoryRepo.findAll();
        List<CategoryRes> res = new ArrayList<>();
        categoriesList.forEach(category ->{
            res.add(categoryMapper.toDtoTwo(category));
        } );

        return res;
    }

}

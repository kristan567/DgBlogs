package com.example.Blog_Application2.controller;

import com.example.Blog_Application2.Service.CategoryService;
import com.example.Blog_Application2.payloads.req.CategoryReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //create
//    @PostMapping("/create-category")
//    public ResponseEntity<CategoryRes> save(@Valid @RequestBody CategoryReq categoryReq) {
//        CategoryRes createCategory = this.categoryService.createCategory(categoryReq);
//        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
//    }

    @PostMapping("/create-category")
    public ResponseEntity<?> save(@Valid @RequestBody CategoryReq categoryReq){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryReq));
    }

    //update
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id, @Valid @RequestBody CategoryReq categoryReq ){
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryReq,id));
    }

    //read
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory((int) id));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategory());
    }

    //delete
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        return ResponseEntity.ok().body(categoryService.deleteCategory((int) id));
    }




}

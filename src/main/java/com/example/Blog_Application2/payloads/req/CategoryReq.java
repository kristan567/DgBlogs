package com.example.Blog_Application2.payloads.req;

import lombok.Data;

@Data
public class CategoryReq {

    private String categoryTitle;
    private String categoryDescription;

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}

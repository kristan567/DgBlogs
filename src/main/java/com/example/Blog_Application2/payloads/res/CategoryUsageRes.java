package com.example.Blog_Application2.payloads.res;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryUsageRes {

    private CategoryRes categoryId;

    private Long occurrence;

    public CategoryUsageRes(CategoryRes categoryId, Long occurrence) {
        this.categoryId = categoryId;
        this.occurrence = occurrence;
    }

    public CategoryRes getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryRes categoryId) {
        this.categoryId = categoryId;
    }

    public Long getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Long occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return "CategoryUsage{" + "categoryId=" + categoryId + ", occurrence=" + occurrence + '}';
    }
}

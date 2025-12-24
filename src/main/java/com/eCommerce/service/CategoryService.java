package com.eCommerce.service;

import com.eCommerce.model.Category;

import java.util.ArrayList;

public interface CategoryService
{
    ArrayList<Category> getallcategory();
    void createcategory(Category category);
    String deletecategory(Long categoryId);
    Category updatecategory(Category category, Long categoryId);
}

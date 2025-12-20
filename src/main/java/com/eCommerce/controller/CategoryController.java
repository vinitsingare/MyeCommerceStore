package com.eCommerce.controller;


import com.eCommerce.model.Category;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.CategoryServiceImple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController
{
    CategoryService categoryservice;

    public CategoryController(CategoryService categoryservice) {
        this.categoryservice = categoryservice;
    }

    @GetMapping("/api/public/categories")
    public ArrayList<Category> getallcategory()
    {
        return categoryservice.getallcategory();
    }

    @PostMapping("/api/public/categories")
    public String createcategory(@RequestBody Category category)
    {
        categoryservice.createcategory(category);
        return "Category added successfully";
    }
}

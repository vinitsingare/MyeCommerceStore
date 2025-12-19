package com.eCommerce.controller;


import com.eCommerce.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController
{
    private ArrayList<Category> categories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public ArrayList<Category> getallcategory()
    {
        return categories;
    }

    @PostMapping("/api/public/categories")
    public String createcategory(@RequestBody Category category)
    {
        categories.add(category);
        return "Category added successfully";
    }
}

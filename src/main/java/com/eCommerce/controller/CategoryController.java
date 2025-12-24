package com.eCommerce.controller;


import com.eCommerce.model.Category;
import com.eCommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class CategoryController {
    CategoryService categoryservice;

    public CategoryController(CategoryService categoryservice) {
        this.categoryservice = categoryservice;
    }

    @GetMapping("/api/public/categories")
    public ArrayList<Category> getallcategory() {
        return categoryservice.getallcategory();
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createcategory(@RequestBody Category category) {
        categoryservice.createcategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created Successfully");

    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deletecategory(@PathVariable Long categoryId) {
        try {
            //categoryservice.deletecategory(categoryId);
            //return ResponseEntity.status(HttpStatus.OK).body("Category Deleted Successfully");
            return ResponseEntity.ok(categoryservice.deletecategory(categoryId));
        }
        catch (ResponseStatusException e)
        {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String> updatecategory(@RequestBody Category category,@PathVariable Long categoryId) {
        try {
            categoryservice.updatecategory(category, categoryId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category Updated Successfully");
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }



}


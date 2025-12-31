package com.eCommerce.controller;


import com.eCommerce.config.AppConstants;
import com.eCommerce.model.Category;
import com.eCommerce.payload.CategoryDTO;
import com.eCommerce.payload.CategoryResponse;
import com.eCommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CategoryController {
    CategoryService categoryservice;

    public CategoryController(CategoryService categoryservice) {
        this.categoryservice = categoryservice;
    }


    @GetMapping("/api/public/categories")
    public CategoryResponse getallcategory
            (
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" ,  defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_CATEGORIES_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_CATEGORIES_ORDER , required = false) String sortOrder
            )
    {
        return categoryservice.getallcategory(pageNumber,pageSize,sortBy,sortOrder);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createcategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryservice.createcategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created Successfully");

    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deletecategory(@PathVariable Long categoryId) {

          // CategoryDTO deletedcategory = categoryservice.deletecategory(categoryId);
            //return ResponseEntity.status(HttpStatus.OK).body("Category Deleted Successfully");
            return ResponseEntity.ok(categoryservice.deletecategory(categoryId));

    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String> updatecategory(@RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId) {
        try {
            categoryservice.updatecategory(categoryDTO, categoryId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category Updated Successfully");
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }



}


package com.eCommerce.service;

import com.eCommerce.model.Category;
import com.eCommerce.repository.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImple implements CategoryService
{
    private final ArrayList<Category> categories = new ArrayList<>();
    //private Long nextid = 1L;

    @Autowired
    private CategoryRespository categoryrespository;

    @Override
    public ArrayList<Category> getallcategory() {
        return (ArrayList<Category>) categoryrespository.findAll();
    }

    @Override
    public void createcategory(Category category) {

        categoryrespository.save(category);
    }

    @Override
    public String deletecategory(Long categoryId) {
        Category savedcategories =  categoryrespository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        categoryrespository.delete(savedcategories);
        return "deleted successfully";
    }

    @Override
    public Category updatecategory(Category category, Long categoryId) {
        Category savedcategories = categoryrespository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        category.setCategoryId(categoryId);
        savedcategories = categoryrespository.save(category);
        return savedcategories;
    }

}

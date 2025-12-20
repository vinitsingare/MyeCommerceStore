package com.eCommerce.service;

import com.eCommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CategoryServiceImple implements CategoryService
{
    private final ArrayList<Category> categories = new ArrayList<>();


    @Override
    public ArrayList<Category> getallcategory() {
        return categories;
    }

    @Override
    public void createcategory(Category category) {
        categories.add(category);
    }


}

package com.eCommerce.service;

import com.eCommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface CategoryService
{
    ArrayList<Category> getallcategory();
    void createcategory(Category category);

}

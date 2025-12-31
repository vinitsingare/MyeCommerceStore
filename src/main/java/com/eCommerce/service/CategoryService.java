package com.eCommerce.service;

import com.eCommerce.model.Category;
import com.eCommerce.payload.CategoryDTO;
import com.eCommerce.payload.CategoryResponse;

import java.util.ArrayList;

public interface CategoryService
{
    CategoryResponse getallcategory(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createcategory(CategoryDTO categoryDTO);
    CategoryDTO deletecategory(Long categoryId);
    CategoryDTO updatecategory(CategoryDTO categoryDTO, Long categoryId);
}

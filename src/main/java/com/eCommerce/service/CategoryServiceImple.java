package com.eCommerce.service;

import com.eCommerce.exception.APIException;
import com.eCommerce.model.Category;
import com.eCommerce.payload.CategoryDTO;
import com.eCommerce.payload.CategoryResponse;
import com.eCommerce.repository.CategoryRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImple implements CategoryService
{
    private final ArrayList<Category> categories = new ArrayList<>();
    //private Long nextid = 1L;

    @Autowired
    private CategoryRespository categoryrespository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getallcategory
            (Integer pageNumber , Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByandOrder);
        Page<Category> categoryPage = categoryrespository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty())
        {
            throw new APIException("No category is been created ");
        }

        ArrayList<CategoryDTO> categoryDTOS = (ArrayList<CategoryDTO>) categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createcategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedcategory = categoryrespository.findByCategoryName(category.getCategoryName());
        if(savedcategory != null){
            throw new APIException("Category already exists of name : " + category.getCategoryName());
        }
        Category category1 = categoryrespository.save(category);
        return modelMapper.map(category1,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deletecategory(Long categoryId) {

        Category savedcategories =  categoryrespository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        categoryrespository.delete(savedcategories);
        return modelMapper.map(savedcategories,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updatecategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedcategories = categoryrespository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

        category.setCategoryId(categoryId);
        savedcategories = categoryrespository.save(category);
        return modelMapper.map(savedcategories,CategoryDTO.class);
    }

}

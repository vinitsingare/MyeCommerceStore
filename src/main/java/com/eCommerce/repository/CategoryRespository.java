package com.eCommerce.repository;

import com.eCommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Long>
{

    Category findByCategoryName(@NotBlank String categoryName);
}

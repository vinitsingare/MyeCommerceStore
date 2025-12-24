package com.eCommerce.repository;

import com.eCommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Long>
{

}

package com.eCommerce.repository;

import com.eCommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByCategoryCategoryId(Long categoryId);
    
    // Paginated version for category filtering
    Page<Product> findByCategoryCategoryId(Long categoryId, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% OR p.description LIKE %:keyword%")
    Page<Product> searchByKeyword(String keyword, Pageable pageable);
}

package com.eCommerce.service;

import com.eCommerce.model.Product;
import com.eCommerce.payload.ProductDTO;
import com.eCommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId, Long categoryId);

    ProductResponse getAllProduct(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    ProductResponse searchByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image);
}

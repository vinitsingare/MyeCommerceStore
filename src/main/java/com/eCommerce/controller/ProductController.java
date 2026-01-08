package com.eCommerce.controller;

import com.eCommerce.config.AppConstants;
import com.eCommerce.model.Product;
import com.eCommerce.payload.ProductDTO;
import com.eCommerce.payload.ProductResponse;
import com.eCommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {
        ProductDTO savedproductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/api/public/product")
    public ResponseEntity<ProductResponse> getAllProduct
            (
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_PRODUCTS_ORDER,required = false) String sortOrder
            )
    {
        ProductResponse productResponse = productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/api/public/product/{categoryId}")
    public ResponseEntity<ProductResponse> getProductByCategoryId
            (
                    @PathVariable Long categoryId,
                    @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                    @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                    @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                    @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_PRODUCTS_ORDER,required = false) String sortOrder
            )
    {
        ProductResponse productResponse = productService.searchByCategoryId(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId,
                                                    @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.deleteProduct(productId, categoryId));
    }

    @PutMapping("/api/admin/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId) {
        ProductDTO updatedproductDTO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updatedproductDTO, HttpStatus.OK);
    }

    @PutMapping("/api/public/product/{productId}/image")
    ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                  @RequestParam("image") MultipartFile image)
    {
        ProductDTO updatedProduct = productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

}

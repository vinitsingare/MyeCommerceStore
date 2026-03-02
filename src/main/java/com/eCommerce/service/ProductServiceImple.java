package com.eCommerce.service;

import com.eCommerce.model.Cart;
import com.eCommerce.model.Category;
import com.eCommerce.model.OrderItem;
import com.eCommerce.model.Product;
import com.eCommerce.payload.CartDTO;
import com.eCommerce.payload.ProductDTO;
import com.eCommerce.payload.ProductResponse;
import com.eCommerce.repository.CartRepository;
import com.eCommerce.repository.CategoryRepository;
import com.eCommerce.repository.OrderItemRepository;
import com.eCommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImple implements ProductService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isProductNotPresent=true;
        List<Product> products = category.getProduct();
        for(Product value : products)
        {
            if(value.getProductName().equals(productDTO.getProductName()))
            {
                isProductNotPresent=false;
                break;
            }
        }

        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setImage("default.png");
            double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else{
            throw new RuntimeException("Product Already Present");
        }
    }

    @Override
    public ProductDTO deleteProduct(Long productId, Long categoryId) {
        Category savedcategories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"CategoryNotFound"));
        Product savedproduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ProductNotFound"));

        // Delete order items first (to handle foreign key constraint)
        List<OrderItem> orderItems = orderItemRepository.findByProductProductId(productId);
        if (orderItems != null && !orderItems.isEmpty()) {
            orderItemRepository.deleteAll(orderItems);
        }

        // Delete from all carts that contain this product
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(), productId));

        productRepository.delete(savedproduct);
        return modelMapper.map(savedproduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId) {
        // Find the product first
        Product savedproduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ProductNotFound"));

        // Delete order items first (to handle foreign key constraint)
        List<OrderItem> orderItems = orderItemRepository.findByProductProductId(productId);
        if (orderItems != null && !orderItems.isEmpty()) {
            orderItemRepository.deleteAll(orderItems);
        }

        // Delete from all carts that contain this product
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(), productId));

        // Delete the product
        productRepository.delete(savedproduct);
        return modelMapper.map(savedproduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct
            (Integer pageNumber,Integer pageSize,String sortBy,String sortOrder)
    {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByandOrder);

        Page<Product> productPage = productRepository.findAll(pageDetails);


        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortOrder)
    {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetailsOfById = PageRequest.of(pageNumber,pageSize,sortByandOrder);

        // FIXED: Use findByCategoryCategoryId instead of findAll to filter by category
        Page<Product> productPageById = productRepository.findByCategoryCategoryId(categoryId, pageDetailsOfById);

        List<Product> products = productPageById.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPageById.getNumber());
        productResponse.setPageSize(productPageById.getSize());
        productResponse.setTotalElements(productPageById.getTotalElements());
        productResponse.setTotalPages(productPageById.getTotalPages());
        productResponse.setLastPage(productPageById.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ProductNotFound"));

        Product product = modelMapper.map(productDTO,Product.class);

        productFromDB.setProductName(product.getProductName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setPrice(product.getPrice());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setSpecialPrice(product.getSpecialPrice());

        Product savedproduct = productRepository.save(productFromDB);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));

        return modelMapper.map(savedproduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) {
        // Get product from DB
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"ProductNotFound"));

        // Upload image to Cloudinary and get the secure URL
        String imageUrl = cloudinaryService.uploadImage(image);

        // Store the full Cloudinary URL in the product
        productFromDB.setImage(imageUrl);

        // Save updated product
        Product updatedProduct = productRepository.save(productFromDB);

        // Return DTO (image field already contains full Cloudinary URL)
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);

        Page<Product> productPage = productRepository.searchByKeyword(keyword, pageDetails);

        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

}

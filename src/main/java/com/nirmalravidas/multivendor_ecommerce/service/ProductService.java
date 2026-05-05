package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.exception.ProductException;
import com.nirmalravidas.multivendor_ecommerce.model.Product;
import com.nirmalravidas.multivendor_ecommerce.model.Seller;
import com.nirmalravidas.multivendor_ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest request, Seller seller) throws ProductException;
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    public Product updateProductStock(Long productId) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public List<Product> searchProduct(String query);
    public Page<Product> getAllProduct(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, String sort, String stock, Integer pageNumber);
    public List<Product> recentlyAddedProduct();
    List<Product> getAllProductBySellerId(Long sellerId);
}

package com.example.product_srtvice.service;

import com.example.product_srtvice.entity.Product;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
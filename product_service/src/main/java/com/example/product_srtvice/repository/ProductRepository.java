package com.example.product_srtvice.repository;

import com.example.product_srtvice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
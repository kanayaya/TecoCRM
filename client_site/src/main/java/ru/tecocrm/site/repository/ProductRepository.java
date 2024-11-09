package ru.tecocrm.site.repository;

import ru.tecocrm.site.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    List<Product> getNewestProducts(int limit);
}

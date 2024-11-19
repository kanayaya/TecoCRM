package ru.tecocrm.site.repository;

import ru.tecocrm.site.entity.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductRepository {
    CompletableFuture<Product> getByIdAsync(Long id);
    CompletableFuture<List<Product>> getAllProductsAsync();
    CompletableFuture<List<Product>> getNewestProductsAsync(int limit);
}

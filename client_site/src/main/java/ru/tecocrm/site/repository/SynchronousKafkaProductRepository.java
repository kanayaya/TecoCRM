package ru.tecocrm.site.repository;

import org.springframework.stereotype.Repository;
import ru.tecocrm.site.entity.Product;
import ru.tecocrm.site.synchronizer.JacksonTypeBuilder;
import ru.tecocrm.site.synchronizer.KafkaMethod;
import ru.tecocrm.site.synchronizer.KafkaMethodArgument;
import ru.tecocrm.site.synchronizer.SynchronousKafkaService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class SynchronousKafkaProductRepository implements ProductRepository {
    private final SynchronousKafkaService source;

    public SynchronousKafkaProductRepository(SynchronousKafkaService source) {
        this.source = source;
    }

    @Override
    public CompletableFuture<Product> getByIdAsync(Long id) {
        JacksonTypeBuilder<Product> productType = new JacksonTypeBuilder<>(Product.class);
        KafkaMethod method = KafkaMethod.builder()
                .topic("productsService")
                .name("getProductById")
                .addArgument(new KafkaMethodArgument("id", id)).build();
        return source.sendAndGet(method, productType)
                .thenApply(productKafkaMessage -> productKafkaMessage.orElseThrow().messageBody());
    }

    @Override
    public CompletableFuture<List<Product>> getAllProductsAsync() {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> getNewestProductsAsync(int limit) {
        return null;
    }
}

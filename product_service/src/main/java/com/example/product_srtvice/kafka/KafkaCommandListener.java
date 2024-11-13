package com.example.product_srtvice.kafka;


import com.example.product_srtvice.entity.Product;
import com.example.product_srtvice.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class KafkaCommandListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaCommandListener.class);

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;

    @Autowired
    public KafkaCommandListener(ProductService productService, ObjectMapper objectMapper,
                                @Value("${app.kafka.listener.threads:10}") int threads) {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    @KafkaListener(topics = "product-commands-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message, Acknowledgment ack) {
        executorService.submit(() -> processMessage(message, ack));
    }

    private void processMessage(String message, Acknowledgment ack) {
        try {
            JsonNode command = objectMapper.readTree(message);
            String action = command.get("action").asText();

            switch (action) {
                case "CREATE":
                    createProduct(command);
                    break;
                case "UPDATE":
                    updateProduct(command);
                    break;
                case "DELETE":
                    deleteProduct(command);
                    break;
                default:
                    logger.info("Unknown action: {}", action);
            }
            ack.acknowledge();
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
    }

    @Transactional
    private void createProduct(JsonNode command) throws Exception {
        Product product = objectMapper.treeToValue(command.get("product"), Product.class);
        productService.createProduct(product);
    }

    @Transactional
    private void updateProduct(JsonNode command) throws Exception {
        Long productId = command.get("id").asLong();
        Product product = objectMapper.treeToValue(command.get("product"), Product.class);
        productService.updateProduct(productId, product);
    }

    @Transactional
    private void deleteProduct(JsonNode command) {
        Long productId = command.get("id").asLong();
        productService.deleteProduct(productId);
    }
}

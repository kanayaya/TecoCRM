package ru.tecocrm.site.synchronizer;

import org.junit.jupiter.api.Test;
import ru.tecocrm.site.entity.Product;

import static org.junit.jupiter.api.Assertions.*;

class KafkaMethodArgumentTest {
    @Test
    public void testCreation() {
        System.out.println(new KafkaMethodArgument("test", 123));

        assertThrows(RuntimeException.class, () -> System.out.println(new KafkaMethodArgument("test2", new Object())),
                "Странно, мы начали успешно обрабатывать пустые объекты? Тогда поправьте в тесте");

        Product product = new Product();
        product.setId(123L);
        product.setName("testProduct");
        System.out.println(new KafkaMethodArgument("test3", product));
    }
}
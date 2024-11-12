package ru.tecocrm.site.entity;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private int priceKopeykas;
    private List<ProductProperty> properties;
}

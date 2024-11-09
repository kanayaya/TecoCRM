package ru.tecocrm.site.entity;

import java.util.List;

public class Product {
    private Long id;
    private String name;
    private String description;
    private int priceKopeykas;
    private List<ProductProperty> properties;
}

package com.example.product_srtvice.entity;

import jakarta.persistence.Entity;

@Entity
public class ProductProperty {
    Long id;
    private String name;
    private String value;
}
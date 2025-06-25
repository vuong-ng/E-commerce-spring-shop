package com.javaproject.springshop.requests;

import java.math.BigDecimal;

import com.javaproject.springshop.model.Category;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
}

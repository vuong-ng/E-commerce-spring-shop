package com.javaproject.springshop.dto;

import java.math.BigDecimal;

import com.javaproject.springshop.model.Product;

import lombok.Data;

@Data
public class CartItemDto {
    private Long itemId;
    private int quantity;
    private BigDecimal unitPrice;
    // private BigDecimal totalPrice;
    private Product product;
}

package com.javaproject.springshop.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
    private Long cartId;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items;
}

package com.javaproject.springshop.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Image> images;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    public Product(String name, String brand, String description, int inventory, BigDecimal price, Category category) {
        this.name = name;
        this.brand=brand;
        this.description = description;
        this.price=price ;
        this.inventory = inventory;
        this.category = category;
    }
}

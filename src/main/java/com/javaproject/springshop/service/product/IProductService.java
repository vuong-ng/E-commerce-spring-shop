package com.javaproject.springshop.service.product;

import java.util.List;

import com.javaproject.springshop.dto.ProductDto;
import com.javaproject.springshop.model.Product;
import com.javaproject.springshop.requests.AddProductRequest;
import com.javaproject.springshop.requests.UpdateProductRequest;

public interface IProductService {
    Product addProduct(AddProductRequest request);

    

    List<Product> getAllProducts();

    Product getProductById(Long Id);

    void deleteProductById(Long Id);

    Product updateProduct(UpdateProductRequest request, Long productId);

    List<Product> getAllProductByCategory(String category);

    List<Product> getAllProductByBrand(String brand);

    List<Product> getAllProductByBrandAndCategory(String brand, String category);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String name, String brand);

    ProductDto convertToDto(Product product);

    List<ProductDto> getConveteredProducts(List<Product> products);

    Long countProductByBrandAndName(String brand, String name);

}

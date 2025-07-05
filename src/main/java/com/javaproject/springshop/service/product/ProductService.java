package com.javaproject.springshop.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.javaproject.springshop.dto.ImageDto;
import com.javaproject.springshop.dto.ProductDto;
import com.javaproject.springshop.exceptions.AlreadyExistsException;
import com.javaproject.springshop.exceptions.ProductNotFoundException;
import com.javaproject.springshop.exceptions.ResourceNotFoundException;
import com.javaproject.springshop.model.Category;
import com.javaproject.springshop.model.Image;
import com.javaproject.springshop.model.Product;
import com.javaproject.springshop.repository.CategoryRepository;
import com.javaproject.springshop.repository.ImageRepository;
import com.javaproject.springshop.repository.ProductRepository;
import com.javaproject.springshop.requests.AddProductRequest;
import com.javaproject.springshop.requests.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        // TODO Auto-generated method stub
        // if category not found 
        // set a new category
        System.out.println(request.getName()+" "+ request.getBrand());
        if (productExists(request.getName(), request.getBrand())) {
            System.out.println(productExists(request.getName(), request.getBrand()));
            throw new AlreadyExistsException(request.getBrand() + " " + request.getName() + " already exists");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
    
    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getDescription(),
            request.getInventory(),
            request.getPrice(),
            category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long Id) {
        // TODO Auto-generated method stub
        return productRepository.findById(Id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long Id) {
        // TODO Auto-generated method stub
        productRepository.findById(Id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        // TODO Auto-generated method stub
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProductByCategory(String category) {
        // TODO Auto-generated method stub
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getAllProductByBrand(String brand) {
        // TODO Auto-generated method stub
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductByBrandAndCategory(String brand, String category) {
        // TODO Auto-generated method stub
        return productRepository.findByBrandAndCategoryName(brand, category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        // TODO Auto-generated method stub
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String name, String brand) {
        // TODO Auto-generated method stub
        return productRepository.findByBrandAndName(brand, name); 
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        // TODO Auto-generated method stub
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConveteredProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
        
    }
}

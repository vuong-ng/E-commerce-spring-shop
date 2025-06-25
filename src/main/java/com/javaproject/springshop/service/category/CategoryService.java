package com.javaproject.springshop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javaproject.springshop.exceptions.AlreadyExistsException;
import com.javaproject.springshop.exceptions.ResourceNotFoundException;
import com.javaproject.springshop.model.Category;
import com.javaproject.springshop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        // TODO Auto-generated method stub
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        // TODO Auto-generated method stub
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        // TODO Auto-generated method stub
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        // TODO Auto-generated method stub
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.  getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        // TODO Auto-generated method stub
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        // TODO Auto-generated method stub
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw new ResourceNotFoundException("Category not found");});
    }
}

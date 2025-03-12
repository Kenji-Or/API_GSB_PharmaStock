package com.cours.api_gsbpharmastock.service;

import com.cours.api_gsbpharmastock.model.Category;
import com.cours.api_gsbpharmastock.repository.CategoryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(final int id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(final Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(final Category category) {
        categoryRepository.delete(category);
    }
}

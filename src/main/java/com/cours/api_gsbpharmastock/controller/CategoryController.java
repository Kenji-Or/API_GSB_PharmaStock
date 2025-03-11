package com.cours.api_gsbpharmastock.controller;

import com.cours.api_gsbpharmastock.model.Category;
import com.cours.api_gsbpharmastock.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public Iterable<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public Optional<Category> getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/category/create")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            Category currentCategory = categoryOptional.get();

            if (updates.containsKey("name")) {
                currentCategory.setName((String) updates.get("name"));
            }

            categoryService.saveCategory(currentCategory);

            // Construire la réponse JSON
            Map<String, Object> response = new HashMap<>();
            response.put("id", currentCategory.getId());
            response.put("name", currentCategory.getName());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            categoryService.deleteCategory(categoryOptional.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

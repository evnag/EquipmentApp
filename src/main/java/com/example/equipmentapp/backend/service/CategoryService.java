package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Category;
import com.example.equipmentapp.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findALL() {
        return categoryRepository.findAll();
    }

    public List<Category> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return categoryRepository.findAll();
        } else {
            return categoryRepository.search(filterText);
        }
    }

    public long count() {
        return categoryRepository.count();
    }

    public void save(Category category) {
        if (category == null) {
            return;
        }
        categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}

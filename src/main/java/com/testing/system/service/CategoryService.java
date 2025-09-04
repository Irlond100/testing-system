package com.testing.system.service;

import com.testing.system.model.Category;
import com.testing.system.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
}

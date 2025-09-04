package com.testing.system.controller;

import com.testing.system.model.Category;
import com.testing.system.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public String listCategories(Model model) {
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("newCategory", new Category());
		return "admin/categories"; // Новая страница
	}
	
	@PostMapping
	public String addCategory(@ModelAttribute Category newCategory) {
		categoryService.save(newCategory);
		return "redirect:/admin/categories";
	}
}
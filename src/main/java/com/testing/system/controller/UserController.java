package com.testing.system.controller;

import com.testing.system.model.User;
import com.testing.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// Метод для отображения формы регистрации
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	// Метод для обработки данных из формы
	@PostMapping("/register")
	public String processRegistration(User user) {
		userService.registerUser(user);
		return "redirect:/login"; // После регистрации перенаправляем на страницу входа
	}
}
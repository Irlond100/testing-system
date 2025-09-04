package com.testing.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Помечаем класс как веб-контроллер
public class HomeController {
	
	// Этот метод будет обрабатывать GET-запросы на главную страницу ("/")
	@GetMapping("/")
	public String home(Model model) {
		// Добавляем в "модель" переменную message со значением
		model.addAttribute("message", "Добро пожаловать в систему тестирования!");
		// Возвращаем имя HTML-файла (без .html), который нужно показать пользователю
		return "index";
	}
}

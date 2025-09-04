package com.testing.system.controller;

import com.testing.system.model.Test;
import com.testing.system.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
	
	private final TestService testService;
	
	public DashboardController(TestService testService) {
		this.testService = testService;
	}
	
	@GetMapping("/home")
	public String home(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/dashboard";
		}
		if (request.isUserInRole("ROLE_INSTRUCTOR")) {
			return "redirect:/instructor/dashboard";
		}
		return "redirect:/candidate/dashboard";
	}
	
	@GetMapping("/candidate/dashboard")
	public String candidateDashboard(Model model) {
		List<Test> tests = testService.findAll();
		model.addAttribute("tests", tests);
		return "candidate_dashboard";
	}
	
	@GetMapping("/instructor/dashboard")
	public String instructorDashboard(Model model) { // Добавляем Model
		List<Test> tests = testService.findAll(); // Получаем список тестов
		model.addAttribute("tests", tests); // Передаем его на страницу
		return "instructor_dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "admin_dashboard";
	}
}


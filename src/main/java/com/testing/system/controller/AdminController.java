package com.testing.system.controller;

import com.testing.system.model.User;
import com.testing.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin") // Все URL в этом контроллере будут начинаться с /admin
public class AdminController {
	
	private final UserService userService;
	
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	// Показывает главную страницу админ-панели (список пользователей)
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "admin/user_list"; // Путь к новому HTML файлу
	}
	
	// Показывает форму для редактирования пользователя
	@GetMapping("/users/edit/{id}")
	public String showEditUserForm(@PathVariable Long id, Model model) {
		User user = userService.findUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Неверный ID пользователя:" + id));
		model.addAttribute("user", user);
		return "admin/edit_user"; // Путь к новому HTML файлу
	}
	
	// Обрабатывает отправку формы редактирования
	@PostMapping("/users/edit/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
		// Устанавливаем ID из URL, чтобы быть уверенными, что мы обновляем нужного пользователя
		user.setId(id);
		userService.updateUser(user);
		return "redirect:/admin/users";
	}
	
	// Обрабатывает удаление пользователя
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin/users";
	}
}
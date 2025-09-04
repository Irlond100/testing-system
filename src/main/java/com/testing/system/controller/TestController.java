package com.testing.system.controller;

import com.testing.system.model.Answer;
import com.testing.system.model.Question;
import com.testing.system.model.Test;
import com.testing.system.model.User;
import com.testing.system.repository.UserRepository;
import com.testing.system.service.QuestionService;
import com.testing.system.service.TestService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
	
	private final TestService testService;
	private final QuestionService questionService;
	private final UserRepository userRepository;
	
	public TestController(TestService testService, QuestionService questionService, UserRepository userRepository) {
		this.testService = testService;
		this.questionService = questionService;
		this.userRepository = userRepository;
	}
	
	// ... (методы getAllTests, showCreateTestForm остаются без изменений)
	@GetMapping
	public String getAllTests(Model model) {
		List<Test> tests = testService.findAll();
		model.addAttribute("tests", tests);
		return "test_list";
	}
	
	@GetMapping("/create")
	public String showCreateTestForm(Model model) {
		model.addAttribute("test", new Test());
		return "create_test";
	}
	
	
	@PostMapping("/create")
	public String createTest(@ModelAttribute Test test, Authentication authentication, RedirectAttributes redirectAttributes) {
		String userEmail = authentication.getName();
		User instructor = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Инструктор не найден: " + userEmail));
		
		Test savedTest = testService.createTest(test, instructor);
		redirectAttributes.addFlashAttribute("successMessage", "Тест '" + savedTest.getTitle() + "' успешно создан! Теперь добавьте вопросы.");
		
		return "redirect:/tests/" + savedTest.getId() + "/add-question";
	}
	
	@GetMapping("/{testId}/add-question")
	public String showAddQuestionForm(@PathVariable Long testId, Model model) {
		Test test = testService.findById(testId)
				.orElseThrow(() -> new IllegalArgumentException("Тест не найден: " + testId));
		
		QuestionForm questionForm = new QuestionForm();
		questionForm.setQuestionText("");
		
		List<AnswerForm> answerForms = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			answerForms.add(new AnswerForm());
		}
		questionForm.setAnswers(answerForms);
		
		model.addAttribute("test", test);
		model.addAttribute("questionForm", questionForm);
		return "add_question";
	}
	
	@PostMapping("/{testId}/add-question")
	public String addQuestion(@PathVariable Long testId, @ModelAttribute QuestionForm questionForm, RedirectAttributes redirectAttributes) {
		Test test = testService.findById(testId)
				.orElseThrow(() -> new IllegalArgumentException("Тест не найден: " + testId));
		
		questionService.saveQuestionFromForm(questionForm, test);
		
		redirectAttributes.addFlashAttribute("successMessage", "Вопрос успешно добавлен!");
		return "redirect:/tests/" + testId + "/add-question";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		testService.deleteTestById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Тест успешно удален.");
		return "redirect:/instructor/dashboard";
	}
	
	
	// Вспомогательные классы (DTO) для формы
	@Setter
	@Getter
	public static class QuestionForm {
		private String questionText;
		private List<AnswerForm> answers;
		private int correctAnswerIndex;
	}
	@Getter
	@Setter
	public static class AnswerForm {
		private String answerText;
		private boolean isCorrect;
	}
}


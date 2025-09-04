package com.testing.system.service;

import com.testing.system.controller.TestController;
import com.testing.system.model.Answer;
import com.testing.system.model.Question;
import com.testing.system.model.Test;
import com.testing.system.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	
	@Transactional
	public void saveQuestion(Question question) {
		questionRepository.save(question);
	}
	
	@Transactional
	public void saveQuestionFromForm(TestController.QuestionForm form, Test test) {
		Question question = new Question();
		question.setText(form.getQuestionText());
		question.setTest(test);
		
		List<Answer> answers = new ArrayList<>();
		int correctAnswerIndex = form.getCorrectAnswerIndex();
		int currentIndex = 0;
		
		for (TestController.AnswerForm answerForm : form.getAnswers()) {
			if (answerForm.getAnswerText() != null && !answerForm.getAnswerText().trim().isEmpty()) {
				Answer answer = new Answer();
				answer.setText(answerForm.getAnswerText());
				// Устанавливаем флаг isCorrect в зависимости от выбранной радиокнопки
				answer.setCorrect(currentIndex == correctAnswerIndex);
				answer.setQuestion(question);
				answers.add(answer);
			}
			currentIndex++;
		}
		
		if (answers.isEmpty()) {
			throw new IllegalArgumentException("Необходимо предоставить хотя бы один вариант ответа.");
		}
		
		question.setAnswers(answers);
		questionRepository.save(question);
	}
}

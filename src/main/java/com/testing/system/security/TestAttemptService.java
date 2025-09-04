package com.testing.system.service;

import com.testing.system.model.*;
import com.testing.system.repository.AnswerRepository;
import com.testing.system.repository.QuestionRepository;
import com.testing.system.repository.TestAttemptRepository;
import com.testing.system.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestAttemptService {
	
	private final TestAttemptRepository testAttemptRepository;
	private final UserAnswerRepository userAnswerRepository;
	private final TestService testService;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	
	
	public TestAttemptService(TestAttemptRepository testAttemptRepository, UserAnswerRepository userAnswerRepository, TestService testService, QuestionRepository questionRepository, AnswerRepository answerRepository) {
		this.testAttemptRepository = testAttemptRepository;
		this.userAnswerRepository = userAnswerRepository;
		this.testService = testService;
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
	}
	
	@Transactional
	public TestAttempt startTest(Long testId, User user) {
		Test test = testService.findById(testId)
				.orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
		
		TestAttempt attempt = new TestAttempt();
		attempt.setTest(test);
		attempt.setUser(user);
		attempt.setStartTime(LocalDateTime.now());
		
		return testAttemptRepository.save(attempt);
	}
	
	@Transactional
	public void saveAnswer(Long attemptId, Long questionId, Long answerId) {
		TestAttempt attempt = testAttemptRepository.findById(attemptId)
				.orElseThrow(() -> new IllegalArgumentException("Попытка не найдена"));
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new IllegalArgumentException("Ответ не найден"));
		
		UserAnswer userAnswer = new UserAnswer();
		userAnswer.setTestAttempt(attempt);
		userAnswer.setQuestion(question);
		userAnswer.setChosenAnswer(answer);
		
		userAnswerRepository.save(userAnswer);
	}
	
	@Transactional
	public TestAttempt finishTest(Long attemptId) {
		TestAttempt attempt = testAttemptRepository.findById(attemptId)
				.orElseThrow(() -> new IllegalArgumentException("Попытка не найдена"));
		
		List<UserAnswer> userAnswers = attempt.getUserAnswers();
		if (userAnswers.isEmpty()) {
			attempt.setScore(0.0);
		} else {
			long correctAnswersCount = userAnswers.stream()
					.filter(userAnswer -> userAnswer.getChosenAnswer().isCorrect())
					.count();
			double score = ((double) correctAnswersCount / userAnswers.size()) * 100.0;
			attempt.setScore(score);
		}
		
		attempt.setEndTime(LocalDateTime.now());
		return testAttemptRepository.save(attempt);
	}
}

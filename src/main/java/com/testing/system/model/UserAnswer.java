package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_answers")
public class UserAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Связь с попыткой прохождения теста
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "test_attempt_id", nullable = false)
	private TestAttempt testAttempt;
	
	// Связь с вопросом, на который дан ответ
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;
	
	// Связь с выбранным вариантом ответа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", nullable = false)
	private Answer chosenAnswer;
}
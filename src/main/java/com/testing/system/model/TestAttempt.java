package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "test_attempts")
public class TestAttempt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Связь с пользователем, который проходит тест
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	// Связь с тестом, который проходят
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "test_id", nullable = false)
	private Test test;
	
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	private Double score; // Результат в процентах
	
	// Список ответов, данных пользователем в рамках этой попытки
	@OneToMany(mappedBy = "testAttempt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserAnswer> userAnswers = new ArrayList<>();
}



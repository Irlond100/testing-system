package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "tests")
public class Test {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	private String description;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	// Один тест может содержать много вопросов
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions;
	
	// Связь с инструктором, который создал тест
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instructor_id", nullable = false)
	private User instructor;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "test_assignments",
			joinColumns = @JoinColumn(name = "test_id"),
			inverseJoinColumns = @JoinColumn(name = "group_id")
	)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<CandidateGroup> assignedGroups = new HashSet<>();
	
}


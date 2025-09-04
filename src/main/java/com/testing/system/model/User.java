package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<CandidateGroup> candidateGroups = new HashSet<>();
	
	@Id // Помечаем поле как первичный ключ (primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Указываем, что БД сама будет генерировать ID
	private Long id;
	
	@Column(nullable = false, unique = true) // Поле не может быть пустым и должно быть уникальным
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String firstName;
	private String lastName;
	
	@Enumerated(EnumType.STRING) // Указываем, что в БД роль будет храниться как строка (e.g., "ROLE_CANDIDATE")
	@Column(nullable = false)
	private UserRole role;
}


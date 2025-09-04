package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "candidate_groups")
public class CandidateGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	private String description;
	
	// Группа может содержать много пользователей (кандидатов)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_groups",
			joinColumns = @JoinColumn(name = "group_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<User> users = new HashSet<>();
	
	// Этой группе может быть назначено много тестов
	@ManyToMany(mappedBy = "assignedGroups", fetch = FetchType.LAZY)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Test> tests = new HashSet<>();
}

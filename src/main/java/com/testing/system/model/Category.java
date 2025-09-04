package com.testing.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Category parent;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> subcategories = new HashSet<>();
	
	// Связь с тестами. Каскадирование убрано, так как это неверно для данной связи.
	@OneToMany(mappedBy = "category")
	private Set<Test> tests = new HashSet<>();
}



package com.testing.system.repository;

import com.testing.system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	// Находим только корневые категории (у которых нет родителя)
	List<Category> findByParentIsNull();
}
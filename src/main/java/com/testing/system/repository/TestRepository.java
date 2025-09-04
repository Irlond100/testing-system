package com.testing.system.repository;

import com.testing.system.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
	
	@Query("SELECT t FROM Test t JOIN t.assignedGroups g WHERE g.id IN :groupIds")
	List<Test> findTestsByGroupIds(@Param("groupIds") Set<Long> groupIds);
}



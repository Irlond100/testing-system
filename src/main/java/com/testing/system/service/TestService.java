package com.testing.system.service;

import com.testing.system.model.Test;
import com.testing.system.model.User;
import com.testing.system.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
	private final TestRepository testRepository;
	
	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}
	
	public Test createTest(Test test, User instructor) {
		test.setInstructor(instructor);
		return testRepository.save(test);
	}
	
	public List<Test> findAll() {
		return testRepository.findAll();
	}
	
	public Optional<Test> findById(Long testId) {
		return testRepository.findById(testId);
	}

	@Transactional
	public void deleteTestById(Long id) {

		testRepository.deleteById(id);
	}
}
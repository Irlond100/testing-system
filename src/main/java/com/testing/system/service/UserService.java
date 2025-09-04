package com.testing.system.service;

import com.testing.system.model.User;
import com.testing.system.model.UserRole;
import com.testing.system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User registerUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new IllegalStateException("Email already in use");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(UserRole.ROLE_CANDIDATE);
		return userRepository.save(user);
	}
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	// НОВЫЙ МЕТОД: Находим только пользователей с ролью CANDIDATE
	public List<User> findAllCandidates() {
		return userRepository.findAll().stream()
				.filter(user -> user.getRole() == UserRole.ROLE_CANDIDATE)
				.collect(Collectors.toList());
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
}



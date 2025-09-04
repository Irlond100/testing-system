package com.testing.system;

import com.testing.system.model.User;
import com.testing.system.model.UserRole;
import com.testing.system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {
		// Создаем администратора, если его еще нет
		if (userRepository.findByEmail("admin@example.com").isEmpty()) {
			User admin = new User();
			admin.setFirstName("Admin");
			admin.setLastName("User");
			admin.setEmail("admin@example.com");
			admin.setPassword(passwordEncoder.encode("password"));
			admin.setRole(UserRole.ROLE_ADMIN);
			userRepository.save(admin);
			System.out.println("Создан аккаунт администратора: admin@example.com");
		}
		
		// Создаем инструктора, если его еще нет
		if (userRepository.findByEmail("instructor@example.com").isEmpty()) {
			User instructor = new User();
			instructor.setFirstName("Instructor");
			instructor.setLastName("User");
			instructor.setEmail("instructor@example.com");
			instructor.setPassword(passwordEncoder.encode("password"));
			instructor.setRole(UserRole.ROLE_INSTRUCTOR);
			userRepository.save(instructor);
			System.out.println("Создан аккаунт инструктора: instructor@example.com");
		}
	}
}


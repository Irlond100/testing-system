package com.testing.system.repository;

import com.testing.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<User, Long> означает, что этот репозиторий работает с сущностью User, у которой ID типа Long
public interface UserRepository extends JpaRepository<User, Long> {
	
	// Spring Data сам поймет, что нужно найти пользователя по полю email
	Optional<User> findByEmail(String email);
}

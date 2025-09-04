package com.testing.system.config;

import com.testing.system.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomUserDetailsService customUserDetailsService;
	
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						// Разрешаем доступ к статическим ресурсам, главной, регистрации и странице входа
						.requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll()
						// Доступ к тестам для инструкторов и администраторов
						.requestMatchers("/tests/**").hasAnyRole("INSTRUCTOR", "ADMIN")
						// Доступ к админ-панели только для администраторов
						.requestMatchers("/admin/**").hasRole("ADMIN")
						// НОВОЕ ПРАВИЛО: доступ к прохождению теста только для кандидатов
						.requestMatchers("/attempt/**").hasRole("CANDIDATE")
						// Новое, более общее правило
						.requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
						// Это можно оставить или удалить, т.к. верхнее правило его покрывает
						.requestMatchers("/tests/**").hasAnyRole("INSTRUCTOR", "ADMIN")
						// Все остальные запросы требуют аутентификации
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/home", true)
						.permitAll()
				)
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.permitAll()
				);
		return http.build();
	}
}
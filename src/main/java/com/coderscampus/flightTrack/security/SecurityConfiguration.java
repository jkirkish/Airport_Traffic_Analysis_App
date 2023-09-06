package com.coderscampus.flightTrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private UserRepository userRepo;
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	private LoginSuccessHandler loginSuccessHandler;

	public SecurityConfiguration(UserRepository userRepository, JwtAuthenticationFilter jwtAuthenticationFilter,
			LoginSuccessHandler loginSuccessHandler) {
		super();
		this.userRepo = userRepository;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.loginSuccessHandler = loginSuccessHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService(userRepo);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((request) -> {
			request

					.requestMatchers("/api/v1/users/register").permitAll()
					.requestMatchers("/api/v1/users/adminPage").authenticated()   //hasRole("ADMIN_USER")
					.requestMatchers("/airportArrivalSearch/**").authenticated()
					.requestMatchers("/arrival/**").authenticated()
					.requestMatchers("/arrivals/**").authenticated()
					.requestMatchers("/arrivalSearchRequests/**").authenticated()
					.requestMatchers("/departure/**").authenticated()
					.requestMatchers("/departures/**").authenticated()
					.requestMatchers("/editSearch/**").authenticated()
					.requestMatchers("/api/v1/users/index").authenticated()
					.requestMatchers("/search/**").authenticated()
					.requestMatchers("/api/v1/users/login").permitAll()
					.requestMatchers("/user/**").authenticated()  //hasAnyRole("ADMIN_USER", "USER_USER")
					.requestMatchers("/api/v1/users/users/**").authenticated();     //hasRole("ADMIN_USER");

		})

				.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin(login -> {
					login.loginPage("/api/v1/users/login");
					login.failureUrl("/api/v1/users/login-error");
					login.successHandler(loginSuccessHandler);
					login.permitAll();
				});

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		return daoAuthenticationProvider;

	}
}

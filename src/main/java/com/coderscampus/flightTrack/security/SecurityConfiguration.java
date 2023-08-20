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
	
	public SecurityConfiguration(UserRepository userRepo,JwtAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.userRepo = userRepo;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(AbstractHttpConfigurer::disable)
		    .authorizeHttpRequests((request) -> {
			request
			    .requestMatchers("/api/v1/users").permitAll()
			    .requestMatchers("/api/v1/users/**").permitAll()
			    .anyRequest().authenticated();
//                .requestMatchers("/register").permitAll()
//                .requestMatchers("/adminPage").hasRole("ADMIN")
//                .requestMatchers("/airportArrivalSearch").hasRole("USER")
//                .requestMatchers("/arrival").authenticated()
//                .requestMatchers("/arrivals").authenticated()
//                .requestMatchers("/arrivalSearchRequests").hasAnyRole("ADMIN", "USER")
//                .requestMatchers("/departure.html").authenticated()
//                .requestMatchers("/departures").authenticated()
//                .requestMatchers("/editSearch").authenticated()
//                .requestMatchers("/errorLogin").permitAll()
//                .requestMatchers("/index").permitAll()
//                .requestMatchers("/registerConfirmation").permitAll()
//                .requestMatchers("/user").hasRole("USER")
//                .requestMatchers("/users").hasRole("ADMIN");
		})
		.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    .authenticationProvider(authenticationProvider())
	    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
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

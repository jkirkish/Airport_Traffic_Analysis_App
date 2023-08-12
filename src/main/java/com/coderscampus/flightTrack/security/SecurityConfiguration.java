package com.coderscampus.flightTrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import com.coderscampus.flightTrack.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((request) -> {
			request
                .requestMatchers("/register").permitAll()
                .requestMatchers("/adminPage").hasRole("ADMIN")
                .requestMatchers("/airportArrivalSearch").hasRole("USER")
                .requestMatchers("/arrival").authenticated()
                .requestMatchers("/arrivals").authenticated()
                .requestMatchers("/arrivalSearchRequests").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/departure.html").authenticated()
                .requestMatchers("/departures").authenticated()
                .requestMatchers("/editSearch").authenticated()
                .requestMatchers("/errorLogin").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/registerConfirmation").permitAll()
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/users").hasRole("ADMIN");
		})
		.userDetailsService(userDetailsService())
		.formLogin((form) -> {
			form.loginPage("/login").permitAll();
		});
		
		return http.build();
	}
}

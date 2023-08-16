package com.coderscampus.flightTrack.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("")
    public ResponseEntity<User> signUpUser (@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
		return ResponseEntity.ok(savedUser);
    }
    
}
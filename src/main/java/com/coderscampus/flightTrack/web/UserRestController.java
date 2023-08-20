package com.coderscampus.flightTrack.web;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.response.AuthenticationResponse;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserService userService;
    private RefreshTokenService refreshTokenService;
    
    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            UserService userService, RefreshTokenService refreshTokenService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> signUpUser (@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        
        String accessToken = jwtService.generateToken(new HashMap<>(), savedUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(savedUser.getId());
        
        return ResponseEntity.ok(new AuthenticationResponse(savedUser.getUsername(), accessToken, refreshToken.getRefreshToken()));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signInUser(@RequestBody User user) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser.getId());
        
        return ResponseEntity.ok(new AuthenticationResponse(loggedInUser.getUsername(), accessToken, refreshToken.getRefreshToken()));
    }
}
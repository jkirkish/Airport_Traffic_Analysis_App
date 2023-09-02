package com.coderscampus.flightTrack.web;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.request.RefreshTokenRequest;
import com.coderscampus.flightTrack.response.AuthenticationResponse;
import com.coderscampus.flightTrack.response.RefreshTokenResponse;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.service.UserService;

//@RestController
@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserService userService;
    private RefreshTokenService refreshTokenService;
    
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            UserService userService, RefreshTokenService refreshTokenService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ModelAndView signUpUser(User user) {
    	System.out.println(user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.saveUser(user);
        
        String accessToken = jwtService.generateToken(new HashMap<>(), savedUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(savedUser.getId());

        // You can add any necessary attributes to the model here if needed
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/register"));

        return modelAndView;
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> signInUser(@RequestBody User user) {
//        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
//        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
//        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser.getId());
//        System.out.println(ResponseEntity.ok(new AuthenticationResponse(loggedInUser.getUsername(), accessToken, refreshToken.getRefreshToken())));
//        return ResponseEntity.ok(new AuthenticationResponse(loggedInUser.getUsername(), accessToken, refreshToken.getRefreshToken()));
//    }
    @PostMapping("/login")
    public ModelAndView signInUser(@RequestBody User user) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser.getId());

        // You can add any necessary attributes to the model here if needed
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/index.html"));

        return modelAndView;
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<RefreshTokenResponse> getNewAccessToken (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String accessToken = refreshTokenService.createNewAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(new RefreshTokenResponse(accessToken, refreshTokenRequest.refreshToken()));
    }
}
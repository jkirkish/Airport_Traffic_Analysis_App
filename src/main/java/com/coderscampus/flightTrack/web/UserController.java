package com.coderscampus.flightTrack.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.service.UserService;

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
	
	@GetMapping("/users")
    public String getUsers(ModelMap model) {
		
        List<User> users = userService.findAll();
        model.put("users", users);
		return "users";
	}
	
	@GetMapping("/users/{id}")
	public String getOneUser (ModelMap model, @PathVariable Integer id) {
		User user = userService.findById(id);
		model.put("user", user);
		return "user";
	}
	
	@GetMapping("/register")
	public String getTheUser(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null, null));
		return "register";
	}
	
	@PostMapping("register")
    public String signUpUser(@ModelAttribute User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateToken(new HashMap<>(), savedUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(savedUser.getId());

        // Add data to the model if needed
        model.addAttribute("username", savedUser.getUsername());
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken.getRefreshToken());

        // Return the logical view name
        return "login";  
    }
	@PostMapping("/login")
    public String signInUser(@ModelAttribute User user, Model model) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser.getId());

        // Add data to the model if needed
        model.addAttribute("username", loggedInUser.getUsername());
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken.getRefreshToken());

        // Return the logical view name
        return "index";  
    }
	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getId();
	}
   @PostMapping("/users/{userId}/delete")
   public String postDeleteUser(@PathVariable Integer userId) {
	   userService.delete(userId);
	   return "redirect:/users";
   }
   
}
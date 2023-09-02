package com.coderscampus.flightTrack.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.request.RefreshTokenRequest;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.service.UserService;

@Controller
@RequestMapping("/api/v1/users")
public class LoginController {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	private UserService userService;
	private RefreshTokenService refreshTokenService;

	public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			UserService userService, RefreshTokenService refreshTokenService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin";
	}

	@GetMapping("/index")
	public String getHomePage() {
		return "index";
	}
//	@GetMapping("/login")
//	public String getLoginScreen() {
//		return "login";
//	}

	@GetMapping("/login")
	public String getLoginPage(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null, null,null));
		return "login";
	}

	@GetMapping("/login-error")
	public String getError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping("/users")
	public String getUsers(ModelMap model) {

		List<User> users = userService.findAll();
		model.put("users", users);
		return "users";
	}

	@GetMapping("/users/{id}")
	public String getOneUser(ModelMap model, @PathVariable Integer id) {
		User user = userService.findById(id);
		model.put("user", user);
		return "user";
	}

	@GetMapping("/register")
	public String getTheUser(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null, null,null));
		return "register";
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

package com.coderscampus.flightTrack.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.coderscampus.flightTrack.domain.RefreshToken;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.repository.UserRepository;
import com.coderscampus.flightTrack.request.RefreshTokenRequest;
import com.coderscampus.flightTrack.response.RefreshTokenResponse;
import com.coderscampus.flightTrack.service.JwtService;
import com.coderscampus.flightTrack.service.RefreshTokenService;
import com.coderscampus.flightTrack.service.RoleService;
import com.coderscampus.flightTrack.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@RestController
@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserService userService;
    private RefreshTokenService refreshTokenService;
    private RoleService roleService;
    
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            UserService userService, RefreshTokenService refreshTokenService,RoleService roleService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.roleService = roleService;
    }
    @GetMapping("/adminPage")
	public String getAdminPage() {
		return "adminPage";
	}

	@GetMapping("/index")
	public String getHomePage() {
		return "index";
	}

	@GetMapping("/login")
	public String getLoginPage(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null,null));
		return "login";
	}

	@GetMapping("/login-error")
	public String getError(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null,null));
		model.put("loginError", true);
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
	public String getRegisterPage(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null,null));
		return "register";
	}
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
	  
	    SecurityContextHolder.clearContext();
	    ModelAndView modelAndView = new ModelAndView(new RedirectView("/api/v1/users/login"));
	    return modelAndView;
	}


	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/api/v1/users/users";
	}

	@PostMapping("/users/{userId}/delete")
	public String postDeleteUser(@PathVariable Integer userId) {

		Long userCount = userService.getUserCount();

		if (userCount > 1) {

			refreshTokenService.deleteByUserId(userId);
			userService.delete(userId);
		}

		return "redirect:/api/v1/users/users";
	}

    @PostMapping("/register")
    public ModelAndView signUpUser(User user) {
    	System.out.println(user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.saveUser(user);
        
        String accessToken = jwtService.generateToken(new HashMap<>(), savedUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(savedUser.getId());
        roleService.addAdminUser(savedUser);
        roleService.addNormalUser(savedUser);
        
        // You can add any necessary attributes to the model here if needed
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/api/vi/users/login"));

        return modelAndView;
    }
    
    @PostMapping("/login")
    public ModelAndView signInUser(@RequestBody User user) {
        User loggedInUser = (User) userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateToken(new HashMap<>(), loggedInUser);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loggedInUser.getId());

        // You can add any necessary attributes to the model here if needed
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/airportArrivalSearch"));

        return modelAndView;
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<RefreshTokenResponse> getNewAccessToken (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String accessToken = refreshTokenService.createNewAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(new RefreshTokenResponse(accessToken, refreshTokenRequest.refreshToken()));
    }
}
package com.coderscampus.flightTrack.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users")
    public String getUsers(ModelMap model) {
		
        List<User> users = userService.findAll();
        model.put("users", users);
		return "users";
	}
	
	@GetMapping("/users/{id}")
	public String getOneUser (ModelMap model, @PathVariable Long id) {
		User user = userService.findById(id);
		model.put("user", user);
		return "user";
	}
	
	@GetMapping("/registerConfirmation")
	public String registeredSuccesful() {
		return "registerConfirmation";
		
	}
	
	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		model.put("user", new User());
		return "register";
	}
	@PostMapping("/register")
	public String postCreateUser(User user) {
		userService.saveUser(user);
		return "redirect:/registerConfirmation";
	}
	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getId();
	}
   @PostMapping("/users/{userId}/delete")
   public String postDeleteUser(@PathVariable Long userId) {
	   userService.delete(userId);
	   return "redirect:/users";
   }
   
}
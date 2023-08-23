package com.coderscampus.flightTrack.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin";
	}
	
	@GetMapping("/index")
	public String getHomePage() {
		return "index";
	}
	@GetMapping("/login")
	public String getLoginPage(ModelMap model) {
		model.put("user", new User(null, null, null, null, null, null, null, null, null));
		return "login";
	}
	@GetMapping("/login-error")
	public String getError(Model model) {
		model.addAttribute("loginError",true);		
		return "/login";
	}
	
//	@PostMapping("/login")
//	public String login(String username, String password) {
//		List<User> users = userService.findAll();
//		String url = "redirect:/errorLogin";
//		for (int i = 0; i < users.size(); i++) {
//			{
//				if (users.get(i).getUsername().equalsIgnoreCase(username)&& users.get(i).getPassword().equals(password))
//				{
//					url = "redirect:/index";
//				}
//			}
//		}
//		return url;
//	}

}

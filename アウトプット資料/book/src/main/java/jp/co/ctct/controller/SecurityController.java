package jp.co.ctct.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SecurityController {
	
	@GetMapping("/login")
	public String login(Authentication loginUser) {
		if(loginUser != null && loginUser.isAuthenticated()) {
		return "redirect:/";
		}
		return "login";
	}
	
	@GetMapping("/")
	public String showMain(Model model,Authentication loginUser) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("role", loginUser.getAuthorities());
		model.addAttribute("main", "main::main");
        return "common/layout";
	}
}

package com.lgh.StudyProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgh.StudyProject.repository.UserRepository;

@Controller
public class MainController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String redirectMainForm() {
		return "redirect:/main";
	}
	
	@GetMapping("/main")
	public String mainForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		if (!id.equals("anonymousUser")) {
			Long num = userRepository.findById(id).get().getNum();
			model.addAttribute("id", num);
		} else {
			model.addAttribute("id", "");
		}
		
		return "main";
	}

}

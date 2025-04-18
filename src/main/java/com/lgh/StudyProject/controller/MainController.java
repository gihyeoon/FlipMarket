package com.lgh.StudyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String redirectMainForm() {
		return "redirect:/main";
	}
	
	@GetMapping("/main")
	public String mainForm() {
		
		
		return "main";
	}

}

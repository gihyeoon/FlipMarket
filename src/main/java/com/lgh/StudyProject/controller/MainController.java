package com.lgh.StudyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class MainController {

	@GetMapping("/main")
	public String mainForm() {
		return "main";
	}

	@PostMapping("/main")
	public String main() {
		return "";
	}

}

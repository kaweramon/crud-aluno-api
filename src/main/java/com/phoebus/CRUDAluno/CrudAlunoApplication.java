package com.phoebus.CRUDAluno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class CrudAlunoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudAlunoApplication.class, args);
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}

package edu.pnu.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	@PostMapping("/sign")
	public String postSign(Model model) {
		
		return"";
	}
	
	@PostMapping("/login")
	public String getLogin() {
		return "로그인 성공";
	}
	
	@PostMapping("/member")
	public String getMember() {
		return "member";
	}
	
	@PostMapping("/admin")
	public String getAdmin() {
		return "admin";
	}
}

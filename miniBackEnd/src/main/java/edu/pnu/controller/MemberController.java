package edu.pnu.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.everyone.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class MemberController {
	@PostMapping("/sign")
	public String postSign(Model model) {
		
		return"";
	}
	
	@PostMapping("/login")
	public String getLogin() {
		return "로그인 성공";
	}
	
	@PostMapping("/memberinfo")
	public String getMember() {
		return "member";
	}
	
	@PostMapping("/admin")
	public String getAdmin() {
		return "admin";
	}
}

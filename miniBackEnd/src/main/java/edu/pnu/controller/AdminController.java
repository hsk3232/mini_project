package edu.pnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	
	@GetMapping("/info")
	public String getAdmin() {
		//실패시에는 컨트롤러에 도착도 못하고 securityConfig에 붙잡힘
		System.out.println("[성공] : [AdminController][Admin 진입]"); 
		return "admin";
	}
}

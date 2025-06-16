package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	@Autowired
	private final MemberRepository memberRepo;
	
	@PostMapping("/sign")
	public String postSign(Model model) {
		
		return"";
	}
}

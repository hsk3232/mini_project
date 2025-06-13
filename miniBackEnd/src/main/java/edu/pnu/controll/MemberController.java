package edu.pnu.controll;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	@PostMapping("/sign")
	public String postSign(Model model) {
		
		return"";
	}
}

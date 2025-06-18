package edu.pnu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.member.MemberJoinDTO;
import edu.pnu.service.member.JoinService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class SignController {
	
	private final JoinService joinService;
		
	@PostMapping("/join")
	public String postJoinsave(@RequestBody MemberJoinDTO dto) {
		joinService.postJoinsave(dto);
		return"가입 성공";
	}
	
	 
	
	// POST /api/public/login 요청 → Filter가 잡아서 처리 → JWT 토큰 응답
	// Controller에는 login 메서드가 없어야 정상!
	
	// UsernamePasswordAuthenticationFilter의 기본 URL은 /login이기 때문에
	// /api/public/login도 작동하게 setFilterProcessesUrl 설정 필요!
	
//	@PostMapping("/login") 
//	public String postLogin(@RequestBody Member member) {
//		String username = member.getUsername();
//        UserDetails userDetails = memberService.loadUserByUsername(username);
//		return userDetails.getUsername();
//	}
}
